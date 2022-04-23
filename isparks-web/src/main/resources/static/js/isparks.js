// deno-fmt-ignore-file
// deno-lint-ignore-file
// This code was bundled using `deno bundle` and it's not recommended to edit it manually

class HTTPError extends Error {
    constructor(response, request, options){
        const code = response.status || response.status === 0 ? response.status : "";
        const title = response.statusText || "";
        const status = `${code} ${title}`.trim();
        const reason = status ? `status code ${status}` : "an unknown error";
        super(`Request failed with ${reason}`);
        this.name = "HTTPError";
        this.response = response;
        this.request = request;
        this.options = options;
    }
}
class TimeoutError extends Error {
    constructor(request){
        super("Request timed out");
        this.name = "TimeoutError";
        this.request = request;
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
    return deepMerge({}, ...sources);
};
const mergeHeaders = (source1 = {}, source2 = {})=>{
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
    let returnValue = {};
    let headers = {};
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
const normalizeRetryOptions = (retry = {})=>{
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
const timeout = async (request, abortController, options)=>new Promise((resolve, reject)=>{
        const timeoutID = setTimeout(()=>{
            if (abortController) {
                abortController.abort();
            }
            reject(new TimeoutError(request));
        }, options.timeout);
        void options.fetch(request).then(resolve).catch(reject).then(()=>{
            clearTimeout(timeoutID);
        });
    })
;
const delay = async (ms)=>new Promise((resolve)=>{
        setTimeout(resolve, ms);
    })
;
class Ky {
    constructor(input, options = {}){
        var _a, _b;
        this._retryCount = 0;
        this._input = input;
        this._options = {
            credentials: this._input.credentials || "same-origin",
            ...options,
            headers: mergeHeaders(this._input.headers, options.headers),
            hooks: deepMerge({
                beforeRequest: [],
                beforeRetry: [],
                afterResponse: []
            }, options.hooks),
            method: normalizeRequestMethod((_a = options.method) !== null && _a !== void 0 ? _a : this._input.method),
            prefixUrl: String(options.prefixUrl || ""),
            retry: normalizeRetryOptions(options.retry),
            throwHttpErrors: options.throwHttpErrors !== false,
            timeout: typeof options.timeout === "undefined" ? 10000 : options.timeout,
            fetch: (_b = options.fetch) !== null && _b !== void 0 ? _b : globalThis.fetch.bind(globalThis)
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
            let response = await ky2._fetch();
            for (const hook of ky2._options.hooks.afterResponse){
                const modifiedResponse = await hook(ky2.request, ky2._options, ky2._decorateResponse(response.clone()));
                if (modifiedResponse instanceof globalThis.Response) {
                    response = modifiedResponse;
                }
            }
            ky2._decorateResponse(response);
            if (!response.ok && ky2._options.throwHttpErrors) {
                throw new HTTPError(response, ky2.request, ky2._options);
            }
            if (ky2._options.onDownloadProgress) {
                if (typeof ky2._options.onDownloadProgress !== "function") {
                    throw new TypeError("The `onDownloadProgress` option must be a function");
                }
                if (!supportsStreams) {
                    throw new Error("Streams are not supported in your environment. `ReadableStream` is missing.");
                }
                return ky2._stream(response.clone(), ky2._options.onDownloadProgress);
            }
            return response;
        };
        const isRetriableMethod = ky2._options.retry.methods.includes(ky2.request.method.toLowerCase());
        const result = isRetriableMethod ? ky2._retry(fn) : fn();
        for (const [type, mimeType] of Object.entries(responseTypes)){
            result[type] = async ()=>{
                ky2.request.headers.set("accept", ky2.request.headers.get("accept") || mimeType);
                const response = (await result).clone();
                if (type === "json") {
                    if (response.status === 204) {
                        return "";
                    }
                    if (options.parseJson) {
                        return options.parseJson(await response.text());
                    }
                }
                return response[type]();
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
    const ky2 = (input, options)=>Ky.create(input, validateAndMerge(defaults, options))
    ;
    for (const method of requestMethods){
        ky2[method] = (input, options)=>Ky.create(input, validateAndMerge(defaults, options, {
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
const http = {
    post: function(href, body, headers = {}) {
        const api = ky.create({
            headers: headers
        });
        return api.post(href, {
            body: body
        }).then((response)=>{
            if (String(response.headers.get("Content-Type")).indexOf("application/json") >= 0) {
                return response.json();
            } else {
                return response;
            }
        });
    },
    put: function(href, body, headers = {}) {
        const api = ky.create({
            headers: headers
        });
        return api.put(href, {
            body: body
        }).then((response)=>{
            if (String(response.headers.get("Content-Type")).indexOf("application/json") >= 0) {
                return response.json();
            } else {
                return response;
            }
        });
    },
    patch: function(href, body, headers = {}) {
        const api = ky.create({
            headers: headers
        });
        return api.patch(href, {
            body: body
        }).then((response)=>{
            if (String(response.headers.get("Content-Type")).indexOf("application/json") >= 0) {
                return response.json();
            } else {
                return response;
            }
        });
    },
    get: function(href, headers = {}) {
        const api = ky.create({
            headers: headers
        });
        return api.get(href).then((response)=>{
            if (String(response.headers.get("Content-Type")).indexOf("application/json") >= 0) {
                return response.json();
            } else {
                return response;
            }
        });
    },
    delete: function(href, headers = {}) {
        const api = ky.create({
            headers: headers
        });
        return api.delete(href, {}).then((response)=>{
            if (String(response.headers.get("Content-Type")).indexOf("application/json") >= 0) {
                return response.json();
            } else {
                return response;
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
    prefixUrl: ''
};
const animator = {
    fadeOut: function(e, oncomplete, time) {
        if (typeof e === "string") e = document.getElementById(e);
        if (!time) time = 500;
        let ease = Math.sqrt;
        let start = new Date().getTime();
        animate();
        function animate() {
            let elapsed = new Date().getTime() - start;
            let fraction = elapsed / time;
            if (fraction < 1) {
                var opacity = 1 - ease(fraction);
                e.style.opacity = String(opacity);
                setTimeout(animate, Math.min(25, time - elapsed));
            } else {
                e.style.opacity = "0";
                if (oncomplete) oncomplete(e);
            }
        }
    },
    shake: function(e, oncomplete, distance, time, direction) {
        if (typeof e === "string") {
            e = document.getElementById(e);
        }
        if (!time) {
            time = 500;
        }
        if (!distance) {
            distance = 5;
        }
        let orginalStyle = e.style.cssText;
        e.style.position = "relative";
        let start = new Date().getTime();
        animate();
        function animate() {
            let now = new Date().getTime();
            let elapsed = now - start;
            let fraction = elapsed / time;
            if (fraction < 1) {
                let x = distance * Math.sin(fraction * 4 * Math.PI);
                if (direction && direction === 'y') {
                    e.style.bottom = x + "px";
                } else {
                    e.style.left = x + "px";
                }
                setTimeout(animate, Math.min(25, time - elapsed));
            } else {
                e.style.cssText = orginalStyle;
                if (oncomplete) oncomplete(e);
            }
        }
    }
};
const toast = {
    attributes: {
        display_delay: 3,
        remove_delay: 5,
        background: '#101924',
        color: '#FFFFFF',
        wrapper_position: "top_right",
        positions: [
            'center',
            'top',
            'bottom',
            'center_right',
            'center_left',
            'top_right',
            'top_left',
            'bottom_right',
            'bottom_left'
        ],
        zIndex: "99",
        parent_id: null,
        shadow: "rgba(0,0,0,.5) 0 0 1em",
        wrapper_padding: "1em"
    },
    listener: function() {
        this.cache.job_id = setInterval(()=>{}, 500);
    },
    get_element: function(key) {
        return document.getElementById(key);
    },
    remove_toast: function(key) {
        let toast1 = document.getElementById(key);
        if (toast1) {
            animator.fadeOut(toast1, ()=>{
                setTimeout(()=>{
                    toast1.style.display = "none";
                    setTimeout(()=>{
                        let wrapper = toast1.parentElement;
                        if (wrapper) {
                            wrapper.removeChild(toast1);
                            this.clear_wrapper(wrapper);
                        }
                    }, this.attributes.remove_delay * 1000);
                }, 300);
            }, 300);
        }
    },
    clear_wrapper: function(wrapper) {
        if (wrapper.children.length == 0) {
            wrapper.parentElement.removeChild(wrapper);
        }
    },
    get_wrapper: function(options) {
        if (!options) {
            return;
        }
        let wrapper_position = this.attributes.wrapper_position;
        if (options.position) {
            for(let i in this.attributes.positions){
                if (this.attributes.positions[i] === options.position.toLowerCase() || this.attributes.positions[i] === options.position.toLowerCase().replace("-", "_")) {
                    wrapper_position = this.attributes.positions[i];
                    break;
                }
            }
        }
        let wrapper_id = "toast_wrapper_" + wrapper_position;
        let parent = options.parent || this.attributes.parent_id && document.getElementById(this.attributes.parent_id) || document.body;
        let wrapper;
        let children = parent.children;
        for(let i in children){
            if (typeof children[i] === "object" && children[i].getAttribute("id") === wrapper_id) {
                wrapper = children[i];
                break;
            }
        }
        if (!wrapper) {
            wrapper = window.document.createElement("dev");
            wrapper.setAttribute("id", wrapper_id);
            wrapper.style.zIndex = options.zIndex || this.attributes.zIndex;
            wrapper.style.position = "absolute";
            wrapper.style.top = "0";
            wrapper.style.bottom = "0";
            wrapper.style.right = "0";
            wrapper.style.left = "0";
            wrapper.style.pointerEvents = "none";
            wrapper.style.display = "flex";
            wrapper.style.flexDirection = "column";
            wrapper.style.padding = options.padding || this.attributes.wrapper_padding;
            if (wrapper_position == "center") {
                wrapper.style.justifyContent = "center";
                wrapper.style.alignItems = "center";
            } else if (wrapper_position === "center-right") {
                wrapper.style.justifyContent = "center";
                wrapper.style.alignItems = "flex-end";
            } else if (wrapper_position === "center_left") {
                wrapper.style.justifyContent = "center";
                wrapper.style.alignItems = "flex-start";
            } else if (wrapper_position === "bottom") {
                wrapper.style.justifyContent = "flex-end";
                wrapper.style.alignItems = "center";
            } else if (wrapper_position === "bottom_right") {
                wrapper.style.justifyContent = "flex-end";
                wrapper.style.alignItems = "flex-end";
            } else if (wrapper_position === "bottom_left") {
                wrapper.style.justifyContent = "flex-end";
                wrapper.style.alignItems = "flex-start";
            } else if (wrapper_position === "top") {
                wrapper.style.justifyContent = "flex-start";
                wrapper.style.alignItems = "center";
            } else if (wrapper_position === "top_right") {
                wrapper.style.justifyContent = "flex-start";
                wrapper.style.alignItems = "flex-end";
            } else if (wrapper_position === "top_left") {
                wrapper.style.justifyContent = "flex-start";
                wrapper.style.alignItems = "flex-start";
            }
            wrapper.style.maxWidth = "100%";
            wrapper.style.overflow = "hidden";
            parent.appendChild(wrapper);
        }
        return wrapper;
    },
    show: function(msg, options) {
        if (!options) {
            options = {};
        }
        if (options.delay == 0) {
            return;
        }
        let wrapper = this.get_wrapper(options);
        let timestamp = new Date().getTime() + Math.random().toString(8);
        let key = "toast_" + timestamp;
        if (this.get_element(key)) {
            console.log("弹窗太频繁");
            return;
        }
        let toast2 = window.document.createElement("dev");
        toast2.setAttribute("id", key);
        toast2.style.minWidth = "5em";
        toast2.style.backgroundColor = options.background || this.attributes.background;
        toast2.style.borderRadius = "10px";
        toast2.style.padding = "1em";
        toast2.style.margin = ".3em";
        toast2.style.webkitAnimation = "popup 0.2s ease-in-out";
        toast2.style.animation = "popup 0.2s ease-in-out";
        toast2.style.boxShadow = options.shadow || this.attributes.shadow;
        let toast_p = window.document.createElement("p");
        toast_p.style.color = options.color || this.attributes.color;
        toast_p.setAttribute("id", "toast_p" + timestamp);
        toast_p.style.width = "100%";
        toast_p.style.margin = "0%";
        toast_p.style.display = "inline";
        toast_p.style.fontFamily = "Arial";
        toast_p.style.display = "inline-block";
        toast_p.style.textAlign = "center";
        toast_p.style.lineHeight = "100%";
        let text = window.document.createTextNode(msg);
        toast_p.appendChild(text);
        toast2.appendChild(toast_p);
        let toast_remove = window.document.createElement("span");
        toast_remove.innerText = "x";
        toast_remove.style.color = options.color || this.attributes.color;
        toast_remove.style.margin = '0 0 0 1em';
        toast_remove.style.cursor = 'pointer';
        toast_remove.style.pointerEvents = "auto";
        toast_remove.onclick = ()=>{
            this.remove_toast(key);
        };
        toast_p.appendChild(toast_remove);
        wrapper.appendChild(toast2);
        toast2.style.transition = "all 0.2s ease-in";
        toast2.style.display = "block";
        if (!options.delay || options.delay > 0) {
            setTimeout(()=>{
                this.remove_toast(key);
            }, (options.delay || this.attributes.display_delay) * 1000);
        }
    },
    info: function(msg, options) {
        if (!options) {
            options = {};
        }
        this.show(msg, options);
    },
    warn: function(msg, options) {
        if (!options) {
            options = {};
        }
        options.background = "#99CCCC";
        this.show(msg, options);
    },
    error: function(msg, options) {
        if (!options) {
            options = {};
        }
        options.background = "#FF9999";
        this.show(msg, options);
    }
};
const loadJSLine = function(arr, callback) {
    const jsLine = arr || [];
    const fn = callback || function() {};
    let nextPro = new Promise(function(resolve, reject) {
        resolve(true);
    });
    const len = jsLine.length;
    if (len <= 0) {
        return;
    }
    for(var i = 0; i < len - 1; ++i){
        const url = jsLine[i];
        nextPro = nextPro.then((res)=>{
            return new Promise(function(resolve, reject) {
                loadJS(url, function() {
                    resolve(true);
                });
            });
        });
    }
    nextPro.then((res)=>{
        loadJS(jsLine[len - 1], function() {
            fn();
        });
    });
};
function loadJS(url, callback, configure) {
    const script = document.createElement('script'), fn = callback || function() {};
    const configFn = configure || function() {};
    script.type = 'text/javascript';
    if (script.readyState) {
        script.onreadystatechange = function() {
            if (script.readyState == 'loaded' || script.readyState == 'complete') {
                script.onreadystatechange = null;
                fn();
            }
        };
    } else {
        script.onload = function() {
            fn();
        };
    }
    script.src = url;
    script.async = true;
    configFn(script);
    document.head.appendChild(script);
}
const updateContent = function(parent, html1) {
    if (!parent) {
        return;
    }
    parent.innerHTML = html1;
    reloadScript(parent);
};
const reloadScript = function(parent1) {
    let scripts = parent1.getElementsByTagName("script");
    for(let i = 0; i < scripts.length; i++){
        let script = scripts[i];
        let parent = script.parentElement;
        let script_content = script.innerText;
        script.parentElement.removeChild(script);
        runScript(parent, script_content);
    }
    function runScript(parent, script) {
        let script_dom = document.createElement("script");
        script_dom.type = "text/javascript";
        script_dom.text = script;
        parent.appendChild(script_dom);
    }
};
const html = {
    loadJSLine: loadJSLine,
    loadJS: loadJS,
    updateContent: updateContent
};
const kit = {
    cookies: {
        get (name) {
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
        set (name, value, expiresTime) {
            expiresTime = expiresTime || 8 * 60 * 60 * 1000;
            const expires = new Date();
            expires.setTime(expires.getTime() + expiresTime);
            document.cookie = name + "=" + escape(value) + (expires == null ? "" : ";expires=" + expires.toGMTString());
        },
        delete (name) {
            var exp = new Date();
            exp.setTime(exp.getTime(-1));
            var cval = this.get(name);
            if (cval != null) {
                document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
            }
        }
    },
    client: {
        fingerprint () {
            if (this.isHeadless()) {
                return "";
            }
            return md5(JSON.stringify(this.info()));
        },
        info () {
            return {
                os: this.os(),
                browser: this.browser(),
                ip: this.ip(),
                screen: this.screen(),
                lan: this.language(),
                canf: this.canvasFingerprint(),
                timeZone: this.timeZone()
            };
        },
        browser () {
            const userAgent = navigator.userAgent;
            let browser = "Unknown";
            if (userAgent.indexOf("Opera") > -1 || userAgent.indexOf("OPR") > -1) {
                browser = 'Opera';
            } else if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1) {
                browser = 'IE';
            } else if (userAgent.indexOf("Edge") > -1) {
                browser = 'Edge';
            } else if (userAgent.indexOf("Firefox") > -1) {
                browser = 'Firefox';
            } else if (userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") == -1) {
                browser = 'Safari';
            } else if (userAgent.indexOf("Chrome") > -1 && userAgent.indexOf("Safari") > -1) {
                browser = 'Chrome';
            } else if (!!window.ActiveXObject || "ActiveXObject" in window) {
                browser = 'IE>=11';
            }
            return browser + " on " + this.os();
        },
        language () {
            let result = navigator.language + " in ";
            const ls = navigator.languages;
            result += "[ ";
            for(var i in navigator.languages){
                result += ls[i] + " ";
            }
            result += "]";
            return result;
        },
        cookieEnabled () {
            return navigator.cookieEnabled;
        },
        isHeadless () {
            if (/HeadlessChrome/.test(window.navigator.userAgent)) {
                console.log("Chrome headless detected");
                return true;
            }
            if (navigator.languages == "") {
                console.log("Chrome headless detected");
                return true;
            }
            return false;
        },
        ip () {
            let ip = localStorage.getItem("ip");
            if (ip != null) {
                return ip;
            }
            const url = "http://httpbin.org/get";
            let res = null;
            const request = new XMLHttpRequest();
            request.open('GET', url, false);
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;");
            request.onreadystatechange = function() {
                if (request.readyState == 4 && request.status == 200) {
                    res = JSON.parse(request.responseText);
                }
            };
            request.send();
            if (res != null) {
                ip = res.origin;
                localStorage.setItem("ip", ip);
                return ip;
            } else {
                return "unknown";
            }
        },
        os () {
            const userAgent = navigator.userAgent.toLowerCase();
            let name = 'Unknown';
            let version = 'Unknown';
            if (userAgent.indexOf('win') > -1) {
                name = 'Windows';
                if (userAgent.indexOf('windows nt 5.0') > -1) {
                    version = '2000';
                } else if (userAgent.indexOf('windows nt 5.1') > -1 || userAgent.indexOf('windows nt 5.2') > -1) {
                    version = 'XP';
                } else if (userAgent.indexOf('windows nt 6.0') > -1) {
                    version = 'Vista';
                } else if (userAgent.indexOf('windows nt 6.1') > -1 || userAgent.indexOf('windows 7') > -1) {
                    version = '7';
                } else if (userAgent.indexOf('windows nt 6.2') > -1 || userAgent.indexOf('windows 8') > -1) {
                    version = '8';
                } else if (userAgent.indexOf('windows nt 6.3') > -1) {
                    version = '8.1';
                } else if (userAgent.indexOf('windows nt 6.2') > -1 || userAgent.indexOf('windows nt 10.0') > -1) {
                    version = '10';
                } else {
                    version = 'Unknown';
                }
            } else if (userAgent.indexOf('iphone') > -1) {
                name = 'Iphone';
            } else if (userAgent.indexOf('mac') > -1) {
                name = 'Mac';
            } else if (userAgent.indexOf('x11') > -1 || userAgent.indexOf('unix') > -1 || userAgent.indexOf('sunname') > -1 || userAgent.indexOf('bsd') > -1) {
                name = 'Unix';
            } else if (userAgent.indexOf('linux') > -1) {
                if (userAgent.indexOf('android') > -1) {
                    name = 'Android';
                } else {
                    name = 'Linux';
                }
            } else {
                name = 'Unknown';
            }
            return name + "(" + version + ")";
        },
        screen () {
            return window.screen.width + " x " + window.screen.height + " x " + window.screen.colorDepth;
        },
        canvasFingerprint () {
            const canvas = document.createElement('canvas');
            canvas.style.display = "none";
            document.body.appendChild(canvas);
            const context = canvas.getContext("2d");
            context.font = "18pt Arial";
            context.textBaseline = "top";
            context.fillText("ISparks.", 2, 2);
            const b64 = canvas.toDataURL("image/png").replace("data:image/png;base64,", "");
            return md5(b64);
        },
        timeZone () {
            const tz = new Date().getTimezoneOffset() / 60;
            return tz;
        }
    },
    date: {
        format: function(mis, format) {
            if (!mis || typeof mis !== "number") {
                return "";
            }
            if (!format) {
                format = "yyyy-MM-dd HH:mm:ss";
            }
            let date = new Date(mis);
            if (format.indexOf("yyyy") >= 0) {
                format = format.replace("yyyy", date.getFullYear());
            }
            if (format.indexOf("MM") >= 0) {
                let MM = date.getMonth() + 1;
                format = format.replace("MM", MM < 10 ? "0" + MM : MM);
            } else if (format.indexOf("M") >= 0) {
                format = format.replace("M", date.getMonth() + 1);
            }
            if (format.indexOf("dd") >= 0) {
                let dd = date.getDate();
                format = format.replace("dd", dd < 10 ? "0" + dd : dd);
            } else if (format.indexOf("d") >= 0) {
                format = format.replace("d", date.getDate());
            }
            if (format.indexOf("HH") >= 0) {
                let HH = date.getHours();
                format = format.replace("HH", HH < 10 ? "0" + HH : HH);
            } else if (format.indexOf("H") >= 0) {
                format = format.replace("H", date.getHours());
            }
            if (format.indexOf("hh") >= 0) {
                let hh = date.getHours() - 12;
                format = format.replace("hh", hh < 10 ? "0" + hh : hh);
            } else if (format.indexOf("h") >= 0) {
                format = format.replace("h", date.getHours() - 12);
            }
            if (format.indexOf("mm") >= 0) {
                let mm = date.getMinutes();
                format = format.replace("mm", mm < 10 ? "0" + mm : mm);
            } else if (format.indexOf("m") >= 0) {
                format = format.replace("m", date.getMinutes());
            }
            if (format.indexOf("ss") >= 0) {
                let ss = date.getSeconds();
                format = format.replace("ss", ss < 10 ? "0" + ss : ss);
            } else if (format.indexOf("s") >= 0) {
                format = format.replace("s", date.getSeconds());
            }
            return format;
        }
    },
    object: {
        deepClone: function(obj) {
            let copy;
            if (null == obj || "object" != typeof obj) return obj;
            if (obj instanceof Date) {
                copy = new Date();
                copy.setTime(obj.getTime());
                return copy;
            }
            if (obj instanceof Array) {
                copy = [];
                for(let i = 0, len = obj.length; i < len; i++){
                    copy[i] = kit.object.deepClone(obj[i]);
                }
                return copy;
            }
            if (obj instanceof Function) {
                copy = function() {
                    return obj.apply(this, arguments);
                };
                return copy;
            }
            if (obj instanceof Object) {
                copy = {};
                for(let attr in obj){
                    if (obj.hasOwnProperty(attr)) copy[attr] = kit.object.deepClone(obj[attr]);
                }
                return copy;
            }
            throw new Error("Unable to copy obj as type isn't supported " + obj.constructor.name);
        }
    }
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
                return decodeURI(document.cookie.substring(start, end));
            }
        }
    },
    set: function(name, value, expiresTime) {
        let key_value = name + "=" + encodeURI(value);
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
(function($) {
    'use strict';
    function safeAdd(x, y) {
        var lsw = (x & 65535) + (y & 65535);
        var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
        return msw << 16 | lsw & 65535;
    }
    function bitRotateLeft(num, cnt) {
        return num << cnt | num >>> 32 - cnt;
    }
    function md5cmn(q, a, b, x, s, t) {
        return safeAdd(bitRotateLeft(safeAdd(safeAdd(a, q), safeAdd(x, t)), s), b);
    }
    function md5ff(a, b, c, d, x, s, t) {
        return md5cmn(b & c | ~b & d, a, b, x, s, t);
    }
    function md5gg(a, b, c, d, x, s, t) {
        return md5cmn(b & d | c & ~d, a, b, x, s, t);
    }
    function md5hh(a, b, c, d, x, s, t) {
        return md5cmn(b ^ c ^ d, a, b, x, s, t);
    }
    function md5ii(a, b, c, d, x, s, t) {
        return md5cmn(c ^ (b | ~d), a, b, x, s, t);
    }
    function binlMD5(x, len) {
        x[len >> 5] |= 128 << len % 32;
        x[(len + 64 >>> 9 << 4) + 14] = len;
        var i;
        var olda;
        var oldb;
        var oldc;
        var oldd;
        var a = 1732584193;
        var b = -271733879;
        var c = -1732584194;
        var d = 271733878;
        for(i = 0; i < x.length; i += 16){
            olda = a;
            oldb = b;
            oldc = c;
            oldd = d;
            a = md5ff(a, b, c, d, x[i], 7, -680876936);
            d = md5ff(d, a, b, c, x[i + 1], 12, -389564586);
            c = md5ff(c, d, a, b, x[i + 2], 17, 606105819);
            b = md5ff(b, c, d, a, x[i + 3], 22, -1044525330);
            a = md5ff(a, b, c, d, x[i + 4], 7, -176418897);
            d = md5ff(d, a, b, c, x[i + 5], 12, 1200080426);
            c = md5ff(c, d, a, b, x[i + 6], 17, -1473231341);
            b = md5ff(b, c, d, a, x[i + 7], 22, -45705983);
            a = md5ff(a, b, c, d, x[i + 8], 7, 1770035416);
            d = md5ff(d, a, b, c, x[i + 9], 12, -1958414417);
            c = md5ff(c, d, a, b, x[i + 10], 17, -42063);
            b = md5ff(b, c, d, a, x[i + 11], 22, -1990404162);
            a = md5ff(a, b, c, d, x[i + 12], 7, 1804603682);
            d = md5ff(d, a, b, c, x[i + 13], 12, -40341101);
            c = md5ff(c, d, a, b, x[i + 14], 17, -1502002290);
            b = md5ff(b, c, d, a, x[i + 15], 22, 1236535329);
            a = md5gg(a, b, c, d, x[i + 1], 5, -165796510);
            d = md5gg(d, a, b, c, x[i + 6], 9, -1069501632);
            c = md5gg(c, d, a, b, x[i + 11], 14, 643717713);
            b = md5gg(b, c, d, a, x[i], 20, -373897302);
            a = md5gg(a, b, c, d, x[i + 5], 5, -701558691);
            d = md5gg(d, a, b, c, x[i + 10], 9, 38016083);
            c = md5gg(c, d, a, b, x[i + 15], 14, -660478335);
            b = md5gg(b, c, d, a, x[i + 4], 20, -405537848);
            a = md5gg(a, b, c, d, x[i + 9], 5, 568446438);
            d = md5gg(d, a, b, c, x[i + 14], 9, -1019803690);
            c = md5gg(c, d, a, b, x[i + 3], 14, -187363961);
            b = md5gg(b, c, d, a, x[i + 8], 20, 1163531501);
            a = md5gg(a, b, c, d, x[i + 13], 5, -1444681467);
            d = md5gg(d, a, b, c, x[i + 2], 9, -51403784);
            c = md5gg(c, d, a, b, x[i + 7], 14, 1735328473);
            b = md5gg(b, c, d, a, x[i + 12], 20, -1926607734);
            a = md5hh(a, b, c, d, x[i + 5], 4, -378558);
            d = md5hh(d, a, b, c, x[i + 8], 11, -2022574463);
            c = md5hh(c, d, a, b, x[i + 11], 16, 1839030562);
            b = md5hh(b, c, d, a, x[i + 14], 23, -35309556);
            a = md5hh(a, b, c, d, x[i + 1], 4, -1530992060);
            d = md5hh(d, a, b, c, x[i + 4], 11, 1272893353);
            c = md5hh(c, d, a, b, x[i + 7], 16, -155497632);
            b = md5hh(b, c, d, a, x[i + 10], 23, -1094730640);
            a = md5hh(a, b, c, d, x[i + 13], 4, 681279174);
            d = md5hh(d, a, b, c, x[i], 11, -358537222);
            c = md5hh(c, d, a, b, x[i + 3], 16, -722521979);
            b = md5hh(b, c, d, a, x[i + 6], 23, 76029189);
            a = md5hh(a, b, c, d, x[i + 9], 4, -640364487);
            d = md5hh(d, a, b, c, x[i + 12], 11, -421815835);
            c = md5hh(c, d, a, b, x[i + 15], 16, 530742520);
            b = md5hh(b, c, d, a, x[i + 2], 23, -995338651);
            a = md5ii(a, b, c, d, x[i], 6, -198630844);
            d = md5ii(d, a, b, c, x[i + 7], 10, 1126891415);
            c = md5ii(c, d, a, b, x[i + 14], 15, -1416354905);
            b = md5ii(b, c, d, a, x[i + 5], 21, -57434055);
            a = md5ii(a, b, c, d, x[i + 12], 6, 1700485571);
            d = md5ii(d, a, b, c, x[i + 3], 10, -1894986606);
            c = md5ii(c, d, a, b, x[i + 10], 15, -1051523);
            b = md5ii(b, c, d, a, x[i + 1], 21, -2054922799);
            a = md5ii(a, b, c, d, x[i + 8], 6, 1873313359);
            d = md5ii(d, a, b, c, x[i + 15], 10, -30611744);
            c = md5ii(c, d, a, b, x[i + 6], 15, -1560198380);
            b = md5ii(b, c, d, a, x[i + 13], 21, 1309151649);
            a = md5ii(a, b, c, d, x[i + 4], 6, -145523070);
            d = md5ii(d, a, b, c, x[i + 11], 10, -1120210379);
            c = md5ii(c, d, a, b, x[i + 2], 15, 718787259);
            b = md5ii(b, c, d, a, x[i + 9], 21, -343485551);
            a = safeAdd(a, olda);
            b = safeAdd(b, oldb);
            c = safeAdd(c, oldc);
            d = safeAdd(d, oldd);
        }
        return [
            a,
            b,
            c,
            d
        ];
    }
    function binl2rstr(input) {
        var i;
        var output = '';
        var length32 = input.length * 32;
        for(i = 0; i < length32; i += 8){
            output += String.fromCharCode(input[i >> 5] >>> i % 32 & 255);
        }
        return output;
    }
    function rstr2binl(input) {
        var i;
        var output = [];
        output[(input.length >> 2) - 1] = undefined;
        for(i = 0; i < output.length; i += 1){
            output[i] = 0;
        }
        var length8 = input.length * 8;
        for(i = 0; i < length8; i += 8){
            output[i >> 5] |= (input.charCodeAt(i / 8) & 255) << i % 32;
        }
        return output;
    }
    function rstrMD5(s) {
        return binl2rstr(binlMD5(rstr2binl(s), s.length * 8));
    }
    function rstrHMACMD5(key, data) {
        var i;
        var bkey = rstr2binl(key);
        var ipad = [];
        var opad = [];
        var hash;
        ipad[15] = opad[15] = undefined;
        if (bkey.length > 16) {
            bkey = binlMD5(bkey, key.length * 8);
        }
        for(i = 0; i < 16; i += 1){
            ipad[i] = bkey[i] ^ 909522486;
            opad[i] = bkey[i] ^ 1549556828;
        }
        hash = binlMD5(ipad.concat(rstr2binl(data)), 512 + data.length * 8);
        return binl2rstr(binlMD5(opad.concat(hash), 512 + 128));
    }
    function rstr2hex(input) {
        var hexTab = '0123456789abcdef';
        var output = '';
        var x;
        var i;
        for(i = 0; i < input.length; i += 1){
            x = input.charCodeAt(i);
            output += hexTab.charAt(x >>> 4 & 15) + hexTab.charAt(x & 15);
        }
        return output;
    }
    function str2rstrUTF8(input) {
        return unescape(encodeURIComponent(input));
    }
    function rawMD5(s) {
        return rstrMD5(str2rstrUTF8(s));
    }
    function hexMD5(s) {
        return rstr2hex(rawMD5(s));
    }
    function rawHMACMD5(k, d) {
        return rstrHMACMD5(str2rstrUTF8(k), str2rstrUTF8(d));
    }
    function hexHMACMD5(k, d) {
        return rstr2hex(rawHMACMD5(k, d));
    }
    function md5(string, key, raw) {
        if (!key) {
            if (!raw) {
                return hexMD5(string);
            }
            return rawMD5(string);
        }
        if (!raw) {
            return hexHMACMD5(key, string);
        }
        return rawHMACMD5(key, string);
    }
    if (typeof define === 'function' && define.amd) {
        define(function() {
            return md5;
        });
    } else if (typeof module === 'object' && module.exports) {
        module.exports = md5;
    } else {
        $.md5 = md5;
    }
})(this);
const md5_implement = function(string, key, raw) {
    return md5(string, key, raw);
};
const algorithm = {
    md5: function(s) {
        return md5_implement(s);
    }
};
var ResultType;
(function(ResultType1) {
    ResultType1[ResultType1["SUCCESS"] = 0] = "SUCCESS";
    ResultType1[ResultType1["FAIL"] = 1] = "FAIL";
    ResultType1[ResultType1["ERROR"] = 2] = "ERROR";
})(ResultType || (ResultType = {}));
const AUTH_KEY = "authorization";
const DEFAULT_TOKEN_CACHE = 1000 * 60 * 60 * 24 * 7;
const auth = {
    getToken: function() {
        let token = cache.get(AUTH_KEY);
        return token;
    },
    setToken: function(token) {
        if (token) {
            cache.set(AUTH_KEY, token, DEFAULT_TOKEN_CACHE);
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
            return {};
        }
    },
    removeToken: function() {
        cookies.delete(AUTH_KEY);
        cache.delete(AUTH_KEY);
        return true;
    }
};
const line = "==============================";
const isparks = {
    init: function() {
        console.log("Isparks For Everyone! GitHub -> https://github.com/hdcheng/isparks 📖");
        let url = window.document.location.href;
        let pos = url.indexOf(window.document.location.pathname);
        let prefix = url.substring(0, pos);
        config.prefixUrl = prefix;
    },
    default_fail: function(msg, result) {
        console.log(line);
        console.log(new Date() + " request fail :");
        if (msg) {
            console.log(msg);
        }
        if (result && result.data) {
            console.log(result.data);
        }
    },
    default_error: function(err) {
        console.log(line);
        console.log(new Date() + " request error :");
        console.log(err);
    },
    default_success: function(res, msg) {
        console.log(line);
        console.log(new Date() + " request success :");
        if (msg) {
            console.log(msg);
        }
        if (res) {
            console.log(res);
        }
    },
    request: function() {
        console.log("Not initialized yet!");
    },
    get: function() {},
    post: function() {},
    delete: function() {},
    put: function() {},
    patch: function() {},
    page: function() {},
    next_page: function() {},
    pre_page: function() {},
    cache: cache,
    cookies: cookies,
    auth: auth,
    image: image,
    http: http,
    toast: toast,
    animator: animator,
    html: html,
    kit: kit,
    algorithm: algorithm
};
function resolveResult(result, success = isparks.default_success, fail = isparks.default_fail, error = isparks.default_error) {
    if (result.code) {
        if (result.code == 8101 && success) {
            success(result.data, result.msg, result);
        } else if (result.code == 8102 && fail) {
            fail(result.msg, result);
        } else if (result.code == 8104 && fail) {
            fail(result.msg, result);
        } else if (result.code == 8103 && error) {
            error(result);
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
        if (api.href.startsWith("/")) {
            api.href = api.href.substring(1);
        }
        api.href = config.prefixUrl + "/" + api.href;
    }
    resolve_url_params(api);
    switch(api.method.toUpperCase()){
        case "GET":
            http.get(api.href, DEFAULT_HEADERS).then((res)=>{
                if (res && res.data && res.data.page) {
                    page_info_storage(api, res);
                }
                resolveResult(res, success, fail, error);
            }).catch((err)=>{
                resolveError(err, error);
            });
            break;
        case "POST":
            http.post(api.href, JSON.stringify(api.body), DEFAULT_HEADERS).then((res)=>{
                if (res && res.data && res.data.page) {
                    page_info_storage(api, res);
                }
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
            break;
        case "PATCH":
            http.patch(api.href, JSON.stringify(api.body), DEFAULT_HEADERS).then((res)=>{
                resolveResult(res, success, fail, error);
            }).catch((err)=>{
                resolveError(err, error);
            });
            break;
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
    if (res.code == 8101 && res.data && res.data.page) {
        let title = get_key_from_href(api.href, api.title);
        localStorage.setItem(title + "[page]", typeof res.data.page === 'number' ? res.data.page + "" : "1");
        localStorage.setItem(title + "[total_page]", typeof res.data.totalPage === 'number' ? res.data.totalPage + "" : "1");
        localStorage.setItem(title + "[total_data]", typeof res.data.totalData === 'number' ? res.data.totalData + "" : "0");
        if (api.params) {
            localStorage.setItem(title + "[size]", typeof api.params.size === 'number' ? api.params.size + "" : "10");
        }
    }
};
const is_request_page = function(api, success, fail, error, page, size) {
    api = url_support(api);
    if (api && api.href) {
        if (api.page) {
            page = api.page;
        }
        if (api.size) {
            size = api.size;
        }
        if (api.params && api.params.page) {
            page = api.params.page;
        }
        if (api.params && api.params.size) {
            size = api.params.size;
        }
        let title = get_key_from_href(api.href, api.title);
        if (!page || page && page <= 0) {
            let cache_page = localStorage.getItem(title + "[page]") || 1;
            page = Number(cache_page);
        } else {
            let total_page = localStorage.getItem(title + "[total_page]");
            if (Number(total_page) < page) {
                page = Number(total_page);
            }
        }
        localStorage.setItem(title + "[page]", page + "");
        if (!size || size && size <= 0) {
            let cache_size = localStorage.getItem(title + "[size]") || 10;
            size = Number(cache_size);
        } else {
            let total_data = localStorage.getItem(title + "[total_data]");
            if (Number(total_data) < size) {
                size = Number(total_data);
            }
        }
        localStorage.setItem(title + "[size]", size + "");
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
        let title = get_key_from_href(api.href, api.title);
        let page = localStorage.getItem(title + "[page]") || 0;
        let size = localStorage.getItem(title + "[size]") || 10;
        is_request_page(api, success, fail, error, Number(page) + 1, Number(size));
    }
};
const is_request_page_pre = function(api, success, fail, error) {
    api = url_support(api);
    if (api) {
        let title = get_key_from_href(api.href, api.title);
        let page = localStorage.getItem(title + "[page]") || 2;
        let size = localStorage.getItem(title + "[size]") || 10;
        let pageNumber = Number(page);
        is_request_page(api, success, fail, error, pageNumber > 1 ? pageNumber - 1 : 1, Number(size));
    }
};
const get_key_from_href = function(href, title) {
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
    if (title && typeof title === "string") {
        return title + ":" + temp_href.replace(/\/|:|\./g, '');
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
