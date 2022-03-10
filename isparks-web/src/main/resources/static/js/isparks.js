class HTTPError extends Error {
    constructor(response1, request, options2){
        const code1 = response1.status || response1.status === 0 ? response1.status : "";
        const title = response1.statusText || "";
        const status = `${code1} ${title}`.trim();
        const reason = status ? `status code ${status}` : "an unknown error";
        super(`Request failed with ${reason}`);
        this.name = "HTTPError";
        this.response = response1;
        this.request = request;
        this.options = options2;
    }
}
class TimeoutError extends Error {
    constructor(request1){
        super("Request timed out");
        this.name = "TimeoutError";
        this.request = request1;
    }
}
const isObject = (value)=>value !== null && typeof value === "object"
;
const validateAndMerge = (...sources)=>{
    for (const source of sources){
        if ((!isObject(source) || Array.isArray(source)) && typeof source !== "undefined") {
            throw new TypeError("The `options` argument must be an object");
        }
    }
    return deepMerge({
    }, ...sources);
};
const mergeHeaders = (source1 = {
}, source2 = {
})=>{
    const result = new globalThis.Headers(source1);
    const isHeadersInstance = source2 instanceof globalThis.Headers;
    const source = new globalThis.Headers(source2);
    for (const [key, value] of source.entries()){
        if (isHeadersInstance && value === "undefined" || value === void 0) {
            result.delete(key);
        } else {
            result.set(key, value);
        }
    }
    return result;
};
const deepMerge = (...sources)=>{
    let returnValue = {
    };
    let headers = {
    };
    for (const source of sources){
        if (Array.isArray(source)) {
            if (!Array.isArray(returnValue)) {
                returnValue = [];
            }
            returnValue = [
                ...returnValue,
                ...source
            ];
        } else if (isObject(source)) {
            for (let [key, value] of Object.entries(source)){
                if (isObject(value) && key in returnValue) {
                    value = deepMerge(returnValue[key], value);
                }
                returnValue = {
                    ...returnValue,
                    [key]: value
                };
            }
            if (isObject(source.headers)) {
                headers = mergeHeaders(headers, source.headers);
                returnValue.headers = headers;
            }
        }
    }
    return returnValue;
};
const supportsAbortController = typeof globalThis.AbortController === "function";
const supportsStreams = typeof globalThis.ReadableStream === "function";
const supportsFormData = typeof globalThis.FormData === "function";
const requestMethods = [
    "get",
    "post",
    "put",
    "patch",
    "head",
    "delete"
];
const responseTypes = {
    json: "application/json",
    text: "text/*",
    formData: "multipart/form-data",
    arrayBuffer: "*/*",
    blob: "*/*"
};
const stop = Symbol("stop");
const normalizeRequestMethod = (input)=>requestMethods.includes(input) ? input.toUpperCase() : input
;
const retryMethods = [
    "get",
    "put",
    "head",
    "delete",
    "options",
    "trace"
];
const retryStatusCodes = [
    408,
    413,
    429,
    500,
    502,
    503,
    504
];
const retryAfterStatusCodes = [
    413,
    429,
    503
];
const defaultRetryOptions = {
    limit: 2,
    methods: retryMethods,
    statusCodes: retryStatusCodes,
    afterStatusCodes: retryAfterStatusCodes,
    maxRetryAfter: Number.POSITIVE_INFINITY
};
const normalizeRetryOptions = (retry = {
})=>{
    if (typeof retry === "number") {
        return {
            ...defaultRetryOptions,
            limit: retry
        };
    }
    if (retry.methods && !Array.isArray(retry.methods)) {
        throw new Error("retry.methods must be an array");
    }
    if (retry.statusCodes && !Array.isArray(retry.statusCodes)) {
        throw new Error("retry.statusCodes must be an array");
    }
    return {
        ...defaultRetryOptions,
        ...retry,
        afterStatusCodes: retryAfterStatusCodes
    };
};
const timeout = async (request2, abortController, options1)=>new Promise((resolve, reject)=>{
        const timeoutID = setTimeout(()=>{
            if (abortController) {
                abortController.abort();
            }
            reject(new TimeoutError(request2));
        }, options1.timeout);
        void options1.fetch(request2).then(resolve).catch(reject).then(()=>{
            clearTimeout(timeoutID);
        });
    })
;
const delay = async (ms)=>new Promise((resolve)=>{
        setTimeout(resolve, ms);
    })
;
class Ky {
    constructor(input1, options1 = {
    }){
        var _a, _b;
        this._retryCount = 0;
        this._input = input1;
        this._options = {
            credentials: this._input.credentials || "same-origin",
            ...options1,
            headers: mergeHeaders(this._input.headers, options1.headers),
            hooks: deepMerge({
                beforeRequest: [],
                beforeRetry: [],
                afterResponse: []
            }, options1.hooks),
            method: normalizeRequestMethod((_a = options1.method) !== null && _a !== void 0 ? _a : this._input.method),
            prefixUrl: String(options1.prefixUrl || ""),
            retry: normalizeRetryOptions(options1.retry),
            throwHttpErrors: options1.throwHttpErrors !== false,
            timeout: typeof options1.timeout === "undefined" ? 10000 : options1.timeout,
            fetch: (_b = options1.fetch) !== null && _b !== void 0 ? _b : globalThis.fetch.bind(globalThis)
        };
        if (typeof this._input !== "string" && !(this._input instanceof URL || this._input instanceof globalThis.Request)) {
            throw new TypeError("`input` must be a string, URL, or Request");
        }
        if (this._options.prefixUrl && typeof this._input === "string") {
            if (this._input.startsWith("/")) {
                throw new Error("`input` must not begin with a slash when using `prefixUrl`");
            }
            if (!this._options.prefixUrl.endsWith("/")) {
                this._options.prefixUrl += "/";
            }
            this._input = this._options.prefixUrl + this._input;
        }
        if (supportsAbortController) {
            this.abortController = new globalThis.AbortController();
            if (this._options.signal) {
                this._options.signal.addEventListener("abort", ()=>{
                    this.abortController.abort();
                });
            }
            this._options.signal = this.abortController.signal;
        }
        this.request = new globalThis.Request(this._input, this._options);
        if (this._options.searchParams) {
            const textSearchParams = typeof this._options.searchParams === "string" ? this._options.searchParams.replace(/^\?/, "") : new URLSearchParams(this._options.searchParams).toString();
            const searchParams = "?" + textSearchParams;
            const url = this.request.url.replace(/(?:\?.*?)?(?=#|$)/, searchParams);
            if ((supportsFormData && this._options.body instanceof globalThis.FormData || this._options.body instanceof URLSearchParams) && !(this._options.headers && this._options.headers["content-type"])) {
                this.request.headers.delete("content-type");
            }
            this.request = new globalThis.Request(new globalThis.Request(url, this.request), this._options);
        }
        if (this._options.json !== void 0) {
            this._options.body = JSON.stringify(this._options.json);
            this.request.headers.set("content-type", "application/json");
            this.request = new globalThis.Request(this.request, {
                body: this._options.body
            });
        }
    }
    static create(input, options) {
        const ky2 = new Ky(input, options);
        const fn = async ()=>{
            if (ky2._options.timeout > 2147483647) {
                throw new RangeError(`The \`timeout\` option cannot be greater than ${2147483647}`);
            }
            await Promise.resolve();
            let response1 = await ky2._fetch();
            for (const hook of ky2._options.hooks.afterResponse){
                const modifiedResponse = await hook(ky2.request, ky2._options, ky2._decorateResponse(response1.clone()));
                if (modifiedResponse instanceof globalThis.Response) {
                    response1 = modifiedResponse;
                }
            }
            ky2._decorateResponse(response1);
            if (!response1.ok && ky2._options.throwHttpErrors) {
                throw new HTTPError(response1, ky2.request, ky2._options);
            }
            if (ky2._options.onDownloadProgress) {
                if (typeof ky2._options.onDownloadProgress !== "function") {
                    throw new TypeError("The `onDownloadProgress` option must be a function");
                }
                if (!supportsStreams) {
                    throw new Error("Streams are not supported in your environment. `ReadableStream` is missing.");
                }
                return ky2._stream(response1.clone(), ky2._options.onDownloadProgress);
            }
            return response1;
        };
        const isRetriableMethod = ky2._options.retry.methods.includes(ky2.request.method.toLowerCase());
        const result = isRetriableMethod ? ky2._retry(fn) : fn();
        for (const [type, mimeType] of Object.entries(responseTypes)){
            result[type] = async ()=>{
                ky2.request.headers.set("accept", ky2.request.headers.get("accept") || mimeType);
                const response1 = (await result).clone();
                if (type === "json") {
                    if (response1.status === 204) {
                        return "";
                    }
                    if (options.parseJson) {
                        return options.parseJson(await response1.text());
                    }
                }
                return response1[type]();
            };
        }
        return result;
    }
    _calculateRetryDelay(error) {
        this._retryCount++;
        if (this._retryCount < this._options.retry.limit && !(error instanceof TimeoutError)) {
            if (error instanceof HTTPError) {
                if (!this._options.retry.statusCodes.includes(error.response.status)) {
                    return 0;
                }
                const retryAfter = error.response.headers.get("Retry-After");
                if (retryAfter && this._options.retry.afterStatusCodes.includes(error.response.status)) {
                    let after = Number(retryAfter);
                    if (Number.isNaN(after)) {
                        after = Date.parse(retryAfter) - Date.now();
                    } else {
                        after *= 1000;
                    }
                    if (typeof this._options.retry.maxRetryAfter !== "undefined" && after > this._options.retry.maxRetryAfter) {
                        return 0;
                    }
                    return after;
                }
                if (error.response.status === 413) {
                    return 0;
                }
            }
            const BACKOFF_FACTOR = 0.3;
            return 0.3 * 2 ** (this._retryCount - 1) * 1000;
        }
        return 0;
    }
    _decorateResponse(response) {
        if (this._options.parseJson) {
            response.json = async ()=>{
                return this._options.parseJson(await response.text());
            };
        }
        return response;
    }
    async _retry(fn) {
        try {
            return await fn();
        } catch (error) {
            const ms = Math.min(this._calculateRetryDelay(error), 2147483647);
            if (ms !== 0 && this._retryCount > 0) {
                await delay(ms);
                for (const hook of this._options.hooks.beforeRetry){
                    const hookResult = await hook({
                        request: this.request,
                        options: this._options,
                        error,
                        retryCount: this._retryCount
                    });
                    if (hookResult === stop) {
                        return;
                    }
                }
                return this._retry(fn);
            }
            throw error;
        }
    }
    async _fetch() {
        for (const hook of this._options.hooks.beforeRequest){
            const result = await hook(this.request, this._options);
            if (result instanceof Request) {
                this.request = result;
                break;
            }
            if (result instanceof Response) {
                return result;
            }
        }
        if (this._options.timeout === false) {
            return this._options.fetch(this.request.clone());
        }
        return timeout(this.request.clone(), this.abortController, this._options);
    }
    _stream(response, onDownloadProgress) {
        const totalBytes = Number(response.headers.get("content-length")) || 0;
        let transferredBytes = 0;
        return new globalThis.Response(new globalThis.ReadableStream({
            async start (controller) {
                const reader = response.body.getReader();
                if (onDownloadProgress) {
                    onDownloadProgress({
                        percent: 0,
                        transferredBytes: 0,
                        totalBytes
                    }, new Uint8Array());
                }
                async function read() {
                    const { done , value  } = await reader.read();
                    if (done) {
                        controller.close();
                        return;
                    }
                    if (onDownloadProgress) {
                        transferredBytes += value.byteLength;
                        const percent = totalBytes === 0 ? 0 : transferredBytes / totalBytes;
                        onDownloadProgress({
                            percent,
                            transferredBytes,
                            totalBytes
                        }, value);
                    }
                    controller.enqueue(value);
                    await read();
                }
                await read();
            }
        }));
    }
}
const createInstance = (defaults)=>{
    const ky2 = (input2, options3)=>Ky.create(input2, validateAndMerge(defaults, options3))
    ;
    for (const method of requestMethods){
        ky2[method] = (input2, options3)=>Ky.create(input2, validateAndMerge(defaults, options3, {
                method
            }))
        ;
    }
    ky2.create = (newDefaults)=>createInstance(validateAndMerge(newDefaults))
    ;
    ky2.extend = (newDefaults)=>createInstance(validateAndMerge(defaults, newDefaults))
    ;
    ky2.stop = stop;
    return ky2;
};
const ky = createInstance();
let http = {
    post: function(href, body, headers = {
    }) {
        const api = ky.create({
            headers: headers
        });
        return api.post(href, {
            body: body
        }).then((response2)=>{
            if (response2.headers.get("Content-Type") == "application/json") {
                return response2.json();
            } else {
                return response2;
            }
        });
    },
    put: function(href, body, headers = {
    }) {
        const api = ky.create({
            headers: headers
        });
        return api.put(href, {
            body: body
        }).then((response2)=>{
            if (response2.headers.get("Content-Type") == "application/json") {
                return response2.json();
            } else {
                return response2;
            }
        });
    },
    patch: function(href, body, headers = {
    }) {
        const api = ky.create({
            headers: headers
        });
        return api.patch(href, {
            body: body
        }).then((response2)=>{
            if (response2.headers.get("Content-Type") == "application/json") {
                return response2.json();
            } else {
                return response2;
            }
        });
    },
    get: function(href, headers = {
    }) {
        const api = ky.create({
            headers: headers
        });
        return api.get(href).then((response2)=>{
            if (response2.headers.get("Content-Type") == "application/json") {
                return response2.json();
            } else {
                return response2;
            }
        });
    },
    delete: function(href, headers = {
    }) {
        const api = ky.create({
            headers: headers
        });
        return api.delete(href, {
        }).then((response2)=>{
            if (response2.headers.get("Content-Type") == "application/json") {
                return response2.json();
            } else {
                return response2;
            }
        });
    }
};
const image = {
    base64: function(b) {
        if (b) {
            return new Promise((resolve, reject)=>{
                let reader = new FileReader();
                reader.readAsDataURL(b);
                reader.onload = (e)=>{
                    if (e.target) {
                        let res = e.target.result + "";
                        resolve(res);
                    }
                };
                reader.onerror = (e)=>{
                    reject(e);
                };
            });
        }
    }
};
const cache = {
    cache_prefix: "is_cache_",
    set: function(key, value, expire) {
        if (key) {
            console.log("缓存", key, value);
            let cache_value = "";
            let current_time = new Date().getTime();
            if (expire && expire > 0) {
                cache_value = value + ";" + (current_time + expire * 1000);
            } else {
                cache_value = value + ";" + "-1";
            }
            localStorage.setItem(cache.cache_prefix + key, cache_value);
        }
    },
    get: function(key) {
        if (key) {
            let value = localStorage.getItem(cache.cache_prefix + key);
            if (value) {
                let arr = value.split(";");
                let expireTime = Number.parseInt(arr[1]);
                if (expireTime < 0 || new Date().getTime() <= expireTime) {
                    return arr[0];
                } else {
                    console.log("缓存超时.....");
                    localStorage.removeItem(cache.cache_prefix + key);
                }
            }
            return null;
        }
    },
    delete: function(key) {
        localStorage.removeItem(key);
    }
};
let config = {
    prefixUrl: "http://127.0.0.1:8174/"
};
const cookies = {
    get: function(name) {
        if (document.cookie.length > 0) {
            let start = document.cookie.indexOf(name + "=");
            if (start != -1) {
                start = start + name.length + 1;
                let end = document.cookie.indexOf(";", start);
                if (end == -1) {
                    end = document.cookie.length;
                }
                return unescape(document.cookie.substring(start, end));
            }
        }
    },
    set: function(name, value, expiresTime) {
        let key_value = name + "=" + escape(value);
        expiresTime = expiresTime || 24 * 60 * 60 * 1000;
        const expiresDate = new Date();
        expiresDate.setTime(expiresDate.getTime() + expiresTime);
        let expires = "expires=" + expiresDate;
        document.cookie = key_value + ";" + expires + ";";
    },
    delete: function(name) {
        let value = this.get(name);
        if (value) {
            const expiresDate = new Date();
            expiresDate.setTime(expiresDate.getTime() - 1000);
            document.cookie = name + "=" + value + ";expires=" + expiresDate;
        }
    },
    cookieEnabled: function() {
        return navigator.cookieEnabled;
    }
};
class Result {
    code;
    msg;
    data;
    constructor(code2, data, msg){
        this.code = code2;
        this.data = data;
        this.msg = msg;
    }
}
var ResultType;
(function(ResultType1) {
    ResultType1[ResultType1["SUCCESS"] = 0] = "SUCCESS";
    ResultType1[ResultType1["FAIL"] = 1] = "FAIL";
    ResultType1[ResultType1["ERROR"] = 2] = "ERROR";
})(ResultType || (ResultType = {
}));
const AUTH_KEY = "authorization";
const DEFAULT_TOKEN_CACHE = 1000 * 60 * 60 * 24 * 7;
const auth = {
    getToken: function() {
        let token = cookies.get(AUTH_KEY);
        if (!token) {
            token = cache.get(AUTH_KEY);
        }
        return token;
    },
    setToken: function(token) {
        if (token) {
            if (cookies.cookieEnabled()) {
                cookies.set(AUTH_KEY, token, DEFAULT_TOKEN_CACHE);
            } else {
                cache.set(AUTH_KEY, token, DEFAULT_TOKEN_CACHE);
            }
        }
    },
    getClaims: function() {
        const jwt = this.getToken();
        if (jwt) {
            let parts = jwt.split(".");
            let payload = parts[1];
            payload = payload.replace(/-/g, '+').replace(/_/g, '/');
            return JSON.parse(window.atob(payload));
        } else {
            return {
            };
        }
    },
    removeToken: function() {
        cookies.delete(AUTH_KEY);
        cache.delete(AUTH_KEY);
    }
};
const line = "==============================";
const isparks = {
    init: function() {
        console.log("Isparks For Everyone! GitHub -> https://github.com/hdcheng/isparks 📖");
    },
    default_fail: function(res, msg1 = '') {
        console.log(line);
        console.log(new Date() + " request fail :");
        console.log(msg1 || "");
        console.log(res);
    },
    default_error: function(err) {
        console.log(line);
        console.log(new Date() + " request error :");
        console.log(err);
    },
    default_success: function(res, msg1) {
        console.log(line);
        console.log(new Date() + " request success :");
        console.log(msg1 || '');
        console.log(res);
    },
    request: function() {
        console.log("Not initialized yet!");
    },
    get: function() {
    },
    post: function() {
    },
    delete: function() {
    },
    put: function() {
    },
    patch: function() {
    },
    page: function() {
    },
    next_page: function() {
    },
    pre_page: function() {
    },
    cache: cache,
    cookies: cookies,
    auth: auth,
    image: image,
    http: http
};
function resolveResult(result, success = isparks.default_success, fail = isparks.default_fail, error = isparks.default_error) {
    if (result.code) {
        if (result.code == 8101 && success) {
            success(result.data, result.msg);
            return;
        } else if (result.code == 8102 && fail) {
            fail(result.data, result.msg);
            return;
        } else if (result.code == 8103 && error) {
            error(result);
            return;
        }
    } else if (result.status == 200) {
        if (success) {
            success(result);
        }
    } else {
        resolveError(result, error);
    }
}
function resolveError(result, error) {
    if (error && result) {
        error(result);
    } else if (result) {
        console.log(result);
    }
}
function jwt() {
    return auth.getToken() || new Date().getTime();
}
const DEFAULT_HEADERS = {
    'Content-Type': 'application/json;charset=UTF-8',
    'Authorization': 'Bearer ' + jwt(),
    'Access-Control-Allow-Origin': '*'
};
const is_get = function(api, success, fail, error) {
    api = url_support(api);
    api.method = "GET";
    is_request(api, success, fail, error);
};
const is_post = function(api, success, fail, error) {
    api = url_support(api);
    api.method = "POST";
    is_request(api, success, fail, error);
};
const is_delete = function(api, success, fail, error) {
    api = url_support(api);
    api.method = "DELETE";
    is_request(api, success, fail, error);
};
const is_put = function(api, success, fail, error) {
    api = url_support(api);
    api.method = "PUT";
    is_request(api, success, fail, error);
};
const is_patch = function(api, success, fail, error) {
    api = url_support(api);
    api.method = "PATCH";
    is_request(api, success, fail, error);
};
const is_request = function(api, success, fail, error) {
    api = url_support(api);
    if (!api || !api.href) {
        console.log("request api is invalid : " + api);
        return;
    }
    if (!api.method) {
        api.method = "GET";
    }
    if (config.prefixUrl && api.href.indexOf(config.prefixUrl) != 0) {
        api.href = config.prefixUrl + api.href;
    }
    resolve_url_params(api);
    switch(api.method.toUpperCase()){
        case "GET":
            http.get(api.href, DEFAULT_HEADERS).then((res)=>{
                page_info_storage(api, res);
                resolveResult(res, success, fail, error);
            }).catch((err)=>{
                resolveError(err, error);
            });
            break;
        case "POST":
            http.post(api.href, JSON.stringify(api.body), DEFAULT_HEADERS).then((res)=>{
                resolveResult(res, success, fail, error);
            }).catch((err)=>{
                resolveError(err, error);
            });
            break;
        case "DELETE":
            http.delete(api.href, DEFAULT_HEADERS).then((res)=>{
                resolveResult(res, success, fail, error);
            }).catch((err)=>{
                resolveError(err, error);
            });
            break;
        case "PUT":
            http.put(api.href, JSON.stringify(api.body), DEFAULT_HEADERS).then((res)=>{
                resolveResult(res, success, fail, error);
            }).catch((err)=>{
                resolveError(err, error);
            });
        case "PATCH":
            http.patch(api.href, JSON.stringify(api.body), DEFAULT_HEADERS).then((res)=>{
                resolveResult(res, success, fail, error);
            }).catch((err)=>{
                resolveError(err, error);
            });
    }
};
const resolve_url_params = function(api) {
    if (!api) {
        return;
    }
    if (api.params) {
        if (api.href.indexOf("?") < 0) {
            api.href += "?";
        } else {
            api.href += "&";
        }
        let url_params = "";
        for(let i in api.params){
            url_params += "&";
            url_params += i;
            url_params += "=";
            url_params += api.params[i];
        }
        if (url_params.length > 0) {
            url_params = url_params.substring(1, url_params.length);
        }
        api.href += url_params;
    }
};
const page_info_storage = function(api, res) {
    if (res.code == 8101 && res.data.page) {
        let title1 = get_key_from_href(api.href, api.title);
        localStorage.setItem(title1 + "[page]", res.data.page + "");
        localStorage.setItem(title1 + "[total_page]", res.data.totalPage + "");
        localStorage.setItem(title1 + "[total_data]", res.data.totalData + "");
        localStorage.setItem(title1 + "[size]", res.data.size + "");
    }
};
const is_request_page = function(api, success, fail, error, page, size) {
    api = url_support(api);
    if (api && api.href) {
        if (api.page) {
            page = api.page;
        }
        if (api.params && api.params.page) {
            page = api.params.page;
        }
        if (api.params && api.params.size) {
            page = api.params.size;
        }
        let title1 = get_key_from_href(api.href, api.title);
        if (!page || page && page <= 0) {
            let cache_page = localStorage.getItem(title1 + "[page]") || 1;
            page = Number(cache_page);
        } else {
            let total_page = localStorage.getItem(title1 + "[total_page]");
            if (Number(total_page) < page) {
                page = Number(total_page);
            }
        }
        localStorage.setItem(title1 + "[page]", page + "");
        if (!size || size && size <= 0) {
            let cache_size = localStorage.getItem(title1 + "[size]") || 10;
            size = Number(cache_size);
        } else {
            let total_data = localStorage.getItem(title1 + "[total_data]");
            if (Number(total_data) < size) {
                size = Number(total_data);
            }
        }
        localStorage.setItem(title1 + "[size]", size + "");
        if (api.params) {
            api.params.page = page;
            api.params.size = size;
        } else {
            api.params = {
                page: page,
                size: size
            };
        }
        is_request(api, success, fail, error);
    }
};
const is_request_page_next = function(api, success, fail, error) {
    api = url_support(api);
    if (api) {
        let title1 = get_key_from_href(api.href, api.title);
        let page = localStorage.getItem(title1 + "[page]") || 0;
        let size = localStorage.getItem(title1 + "[size]") || 10;
        is_request_page(api, success, fail, error, Number(page) + 1, Number(size));
    }
};
const is_request_page_pre = function(api, success, fail, error) {
    api = url_support(api);
    if (api) {
        let title1 = get_key_from_href(api.href, api.title);
        let page = localStorage.getItem(title1 + "[page]") || 2;
        let size = localStorage.getItem(title1 + "[size]") || 10;
        let pageNumber = Number(page);
        is_request_page(api, success, fail, error, pageNumber > 1 ? pageNumber - 1 : 1, Number(size));
    }
};
const get_key_from_href = function(href, title1) {
    if (!href) {
        return "null";
    }
    let temp_href = "";
    if (href.indexOf(config.prefixUrl) >= 0) {
        temp_href = href.replace(config.prefixUrl, "");
    } else {
        temp_href = href;
    }
    if (temp_href.indexOf("?") > 0) {
        temp_href = temp_href.substring(0, temp_href.indexOf("?"));
    }
    if (title1 && typeof title1 === "string") {
        return title1 + ":" + temp_href.replace(/\/|:|\./g, '');
    } else {
        return temp_href.replace(/\/|:|\./g, '');
    }
};
const url_support = function(api) {
    if (api && typeof api === "string") {
        let href = api;
        api = {
            href: href
        };
    }
    return api;
};
isparks.get = is_get;
isparks.post = is_post;
isparks.delete = is_delete;
isparks.put = is_put;
isparks.patch = is_patch;
isparks.request = is_request;
isparks.page = is_request_page;
isparks.next_page = is_request_page_next;
isparks.pre_page = is_request_page_pre;
var i$ = isparks;
i$.init();
