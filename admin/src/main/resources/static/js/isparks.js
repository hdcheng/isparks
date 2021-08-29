class HTTPError extends Error {
    constructor(response1, request, options2){
        const code1 = response1.status || response1.status === 0 ? response1.status : "";
        const title1 = response1.statusText || "";
        const status = `${code1} ${title1}`.trim();
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
var Request_Method;
(function(Request_Method1) {
    Request_Method1[Request_Method1["POST"] = 0] = "POST";
    Request_Method1[Request_Method1["GET"] = 1] = "GET";
    Request_Method1[Request_Method1["DELETE"] = 2] = "DELETE";
    Request_Method1[Request_Method1["PUT"] = 3] = "PUT";
    Request_Method1[Request_Method1["PATCH"] = 4] = "PATCH";
})(Request_Method || (Request_Method = {
}));
class API {
    title;
    method;
    href;
    content_type;
    body;
    url_params = "";
    path_params = null;
    constructor(title2, href, method = Request_Method.GET, content_type = "application/json"){
        this.title = title2;
        this.method = method;
        this.href = href;
        this.content_type = content_type;
    }
    withData(data) {
        if (data) {
            this.body = data;
        }
        return this;
    }
    withJsonBody(json) {
        if (json) {
            this.body = JSON.stringify(json);
        }
        return this;
    }
    withParams(params) {
        if (this.resolveParamsFunc) {
            this.resolveParamsFunc(params, this);
        }
        return this;
    }
    withPathParams(path_params) {
        if (this.resolvePathParamsFunc) {
            this.resolvePathParamsFunc(path_params, this);
        }
        return this;
    }
    DEFAULT_RESOLVE_PARAMS_FUNC = function(params, api) {
        let urlParams = api.url_params ? api.url_params + "&" : "";
        for(let key in params){
            urlParams += key + "=" + encodeURI(params[key]) + "&";
        }
        if (urlParams.indexOf("&") > -1) {
            urlParams = urlParams.substring(0, urlParams.length - 1);
        }
        if (api.href.indexOf("?") > -1) {
            api.url_params = "&" + urlParams;
        } else {
            api.url_params = "?" + urlParams;
        }
    };
    DEFAULT_PATH_RESOLVE_FUNC = function(path_params, api) {
        if (path_params) {
            for(let key in path_params){
                let key_place = "{" + key + "}";
                if (api.href.indexOf(key_place) > -1) {
                    api.href = api.href.replace(new RegExp(key_place, "g"), path_params[key]);
                }
            }
        }
    };
    resolveParamsFunc = this.DEFAULT_RESOLVE_PARAMS_FUNC;
    resolvePathParamsFunc = this.DEFAULT_PATH_RESOLVE_FUNC;
    withResolveParamsFunc(func) {
        if (func) {
            this.resolveParamsFunc = func;
        }
        return this;
    }
    withResolvePathParamsFunc(func) {
        if (func) {
            this.resolvePathParamsFunc = func;
        }
        return this;
    }
    href_with_params() {
        let href1 = this.href + this.url_params;
        this.url_params = "";
        return href1;
    }
}
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
function jwt() {
    return new Date().getTime();
}
const AUTH_KEY = "authorization";
const apis_map = new Map();
const log_apis = new Map();
const category_apis = new Map();
const admin_apis = new Map();
let DEFAULT_HEADERS = {
    'Content-Type': 'application/json;charset=UTF-8',
    'Authorization': 'Bearer ' + jwt()
};
let http = {
    post: function(href1, body, headers = DEFAULT_HEADERS) {
        const api = ky.create({
            headers: headers,
            prefixUrl: config.prefixUrl
        });
        return api.post(href1, {
            body: body
        }).then((response2)=>{
            return response2.json();
        });
    },
    put: function(href1, body, headers = DEFAULT_HEADERS) {
        const api = ky.create({
            headers: headers,
            prefixUrl: config.prefixUrl
        });
        return api.put(href1, {
            body: body
        }).then((response2)=>{
            return response2.json();
        });
    },
    patch: function(href1, body, headers = DEFAULT_HEADERS) {
        const api = ky.create({
            headers: headers,
            prefixUrl: config.prefixUrl
        });
        return api.patch(href1, {
            body: body
        }).then((response2)=>{
            return response2.json();
        });
    },
    get: function(href1, headers = DEFAULT_HEADERS) {
        const api = ky.create({
            headers: headers,
            prefixUrl: config.prefixUrl
        });
        return api.get(href1).then((response2)=>{
            return response2.json();
        });
    },
    delete: function(href1, headers = DEFAULT_HEADERS) {
        const api = ky.create({
            headers: headers,
            prefixUrl: config.prefixUrl
        });
        return api.delete(href1, {
        }).then((response2)=>{
            return response2.json();
        });
    }
};
const DEFAULT_TOKEN_CACHE = 1000 * 60 * 60 * 24;
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
        const jwt1 = this.getToken();
        if (jwt1) {
            let parts = jwt1.split(".");
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
function init_apis() {
    for (let key of log_apis.keys()){
        apis_map.set(key, log_apis.get(key));
    }
    for (let key1 of category_apis.keys()){
        apis_map.set(key1, category_apis.get(key1));
    }
    for (let key2 of admin_apis.keys()){
        apis_map.set(key2, admin_apis.get(key2));
    }
}
const apis = {
    get: function(key) {
        if (apis_map.size == 0) {
            init_apis();
        }
        let api = apis_map.get(key);
        if (api) {
            return new API(api.title, api.href, api.method, api.content_type).withResolveParamsFunc(api.resolveParamsFunc).withResolvePathParamsFunc(api.resolvePathParamsFunc);
        } else {
            return null;
        }
    },
    size: apis_map.size
};
log_apis.set("log_page", new API("Get Logs", "v1/admin/log/page"));
log_apis.set("log_types", new API("Get Log Types", "v1/admin/log/types"));
log_apis.set("log_delete", new API("Delete Log By Id", "v1/admin/log", Request_Method.DELETE).withResolvePathParamsFunc((params, api)=>{
    if (params && params.id) {
        api.href = "v1/admin/log/" + params.id;
    } else {
        api.href = "v1/admin/log";
    }
}));
category_apis.set("categories", new API("Get Categories", "v1/admin/categories"));
category_apis.set("category_delete", new API("Delete by name", "v1/admin/category/name", Request_Method.DELETE).withResolvePathParamsFunc((params, api)=>{
    if (params && params.name) {
        api.href = "v1/admin/category/name/" + params.name;
    } else {
        api.href = "v1/admin/category/name/";
    }
}));
category_apis.set("category_page", new API("Get category by page", "v1/admin/category/page"));
category_apis.set("category_create", new API("Create category", "v1/admin/category", Request_Method.POST));
category_apis.set("category_update", new API("Create category", "v1/admin/category", Request_Method.PATCH));
const login_api = new API("Authenticate", "v1/admin/authenticate");
admin_apis.set("login", login_api);
admin_apis.set("verify_token", new API("Verify Token", "v1/admin/authenticate", Request_Method.POST, "application/x-www-form-urlencoded;charset=utf-8"));
const logout_api = new API("logout", "v1/admin/logout").withResolvePathParamsFunc((params, api)=>{
    if (params && params.username) {
        api.href = "v1/admin/logout/" + params.username;
    } else {
        api.href = "v1/admin/logout";
    }
});
admin_apis.set("logout", logout_api);
const line = "==============================";
const isparks = {
    init: function() {
        apis.get("");
        console.log("Isparks For Everyone! GitHub -> https://github.com/hdcheng/isparks 📖");
    },
    default_fail: function(res, msg1 = '') {
        console.log(line);
        console.log(new Date() + "request fail :");
        console.log(msg1);
        console.log(res);
    },
    default_error: function(err) {
        console.log(line);
        console.log(new Date() + "request error :");
        console.log(err);
    },
    default_success: function(res, msg1) {
        console.log(line);
        console.log(new Date() + " request success :");
        console.log(msg1);
        console.log(res);
    },
    request: function() {
        console.log("Not initialized yet");
    },
    page: function() {
    },
    next_page: function() {
    },
    pre_page: function() {
    },
    cache: cache,
    cookies: cookies,
    auth: auth
};
function resolveResult(result, success = isparks.default_success, fail = isparks.default_fail, error = isparks.default_error) {
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
    console.log(result);
}
function resolveError(result, error) {
    if (error && result) {
        error(result);
    } else if (result) {
        console.log(result);
    }
}
const is_request = function(api, success, fail, error) {
    if (!api) {
        return;
    }
    switch(api.method){
        case Request_Method.GET:
            http.get(api.href_with_params()).then((res)=>{
                page_info_storage(api, res);
                resolveResult(res, success, fail, error);
            }).catch((err)=>{
                resolveError(err, error);
            });
            break;
        case Request_Method.POST:
            http.post(api.href_with_params(), api.body).then((res)=>{
                resolveResult(res, success, fail, error);
            }).catch((err)=>{
                resolveError(err, error);
            });
            break;
        case Request_Method.DELETE:
            http.delete(api.href_with_params()).then((res)=>{
                resolveResult(res, success, fail, error);
            }).catch((err)=>{
                resolveError(err, error);
            });
            break;
        case Request_Method.PUT:
            http.put(api.href_with_params(), api.body).then((res)=>{
                resolveResult(res, success, fail, error);
            }).catch((err)=>{
                resolveError(err, error);
            });
        case Request_Method.PATCH:
            http.patch(api.href_with_params(), api.body).then((res)=>{
                resolveResult(res, success, fail, error);
            }).catch((err)=>{
                resolveError(err, error);
            });
    }
};
const page_info_storage = function(api, res) {
    if (res.code == 8101 && res.data.page) {
        let title3 = api.title;
        localStorage.setItem(title3 + "[page]", res.data.page + "");
        localStorage.setItem(title3 + "[total_page]", res.data.totalPage + "");
        localStorage.setItem(title3 + "[total_data]", res.data.totalData + "");
        localStorage.setItem(title3 + "[size]", res.data.size + "");
    }
};
const is_request_page = function(api, page, size, success, fail, error) {
    if (api) {
        let title3 = api.title;
        if (!page || page && page <= 0) {
            page = 1;
        } else {
            let total_page = localStorage.getItem(title3 + "[total_page]");
            if (Number(total_page) < page) {
                page = Number(total_page);
            }
        }
        localStorage.setItem(title3 + "[page]", page + "");
        if (!size || size && size <= 0) {
            size = 10;
        } else {
            let total_data = localStorage.getItem(title3 + "[total_data]");
            if (Number(total_data) < size) {
                size = Number(total_data);
            }
        }
        localStorage.setItem(title3 + "[size]", size + "");
        api.withParams({
            page: page,
            size: size
        });
        is_request(api, success, fail, error);
    }
};
const is_request_page_next = function(api, success, fail, error) {
    if (api) {
        let title3 = api.title;
        let page = localStorage.getItem(title3 + "[page]") || 0;
        let size = localStorage.getItem(title3 + "[size]") || 10;
        is_request_page(api, Number(page) + 1, Number(size), success, fail, error);
    }
};
const is_request_page_pre = function(api, success, fail, error) {
    if (api) {
        let title3 = api.title;
        let page = localStorage.getItem(title3 + "[page]") || 2;
        let size = localStorage.getItem(title3 + "[size]") || 10;
        let pageNumber = Number(page);
        is_request_page(api, pageNumber > 1 ? pageNumber - 1 : 1, Number(size), success, fail, error);
    }
};
isparks.request = is_request;
isparks.page = is_request_page;
isparks.next_page = is_request_page_next;
isparks.pre_page = is_request_page_pre;
var i$ = isparks;
i$.init();
