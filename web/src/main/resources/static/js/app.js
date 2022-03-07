'use strict'
window.onload = function(){

    const jsLine = ["/static/js/utils.js","/static/js/md5.min.js","/static/js/vue.min.js","/static/js/axios.min.js","/static/js/isparks.js"];
    
    loadJSLine(jsLine,function(){
        axios.interceptors.request.use(
            config => {
                const token = Utils.auth.getToken();
                if (token) {
                    config.headers.Authorization = token;//把localStorage的token放在Authorization里
                }
                return config;
            },
            function(err) {
                console.log("失败信息" + err);
            }
        );

        init();    
    });

}

function init(){
    
    const config = {
        baseUrl:"http://127.0.0.1:8174",
    }

    if(typeof(pageLoaded) == 'function'){
        // 页面加载完,执行
        pageLoaded(config);
    }else{
        const isMid = document.getElementById("is-mid")
        if(isMid && "error" == isMid.innerText){
            window.location.href = "/error/404?reason=页面"+document.URL+"不存在";
        }
    }

    if(typeof(sidbarLoaded) == 'function'){
        sidbarLoaded();
    }

    document.querySelectorAll("[is-loading]").forEach((item,i,obj)=>{
        item.removeAttribute("is-loading");
    })
}

/**
 * @param {*} url js 链接
 * @param {*} callback 加载完成后执行的回调函数 
 * @param {*} configure 配置script标签的回调函数
 */
function loadJS(url,callback,configure){
    const script = document.createElement('script'),
    fn = callback || function(){};
    const configFn = configure || function(){}
    script.type = 'text/javascript';
    if(script.readyState){//IE
        script.onreadystatechange = function(){
            if( script.readyState == 'loaded' || script.readyState == 'complete' ){
                script.onreadystatechange = null;
                fn();
            }
        };
    }else{//其他浏览器
        script.onload = function(){
            fn();
        };
    }
    script.src = url;
    script.async = true; 
    configFn(script);
    //document.getElementsByTagName('head')[0].appendChild(script);
    document.head.appendChild(script);
}
/**
 * 按顺序加载js文件
 * 
 * @param {*} arr 
 * @param {*} callback 
 */
function loadJSLine(arr,callback){

    const jsLine = arr || [];
    
    const fn = callback || function(){};

    let nextPro = new Promise(function (resolve, reject) {
        resolve(true);
    });

    const len = jsLine.length;

    if(len <= 0){return;}

    for(var i = 0 ; i < len - 1 ; ++i){
        const url = jsLine[i];
        nextPro = nextPro.then(res => {
            return new Promise(function (resolve, reject) {
                loadJS(url,function(){
                    resolve(true);
                });
            })
        });
    }
    
    nextPro.then(res => {
        loadJS(jsLine[len-1],function(){
            fn();
        });
    });
}

/**
 更新dom 元素
*/
function loadContent(parent,path,fragment){
    if( !parent || !page ){
        return;
    }

    parent.innerHTML = "";

    let xhr ;
    if(XMLHttpRequest){
        xhr = new XMLHttpRequest();
    }

    if(!xhr){
        console.log("目前浏览器版本较低，请使用更高版本的浏览器。");
        return;
    }

    const url = "/api/admin/fragment";
    xhr.open('post',url,true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.setRequestHeader("Accept", "application/json, text/plain, */*");
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200 || xhr.status == 304) {
            let res = JSON.parse(xhr.responseText);
            parent.innerHTML = res.data;
            loadScript(parent);
        }
    };
    let jsonData = {
        "path":path,
        "fragment":fragment
   }
   xhr.send(JSON.stringify(jsonData));
}
// 重新加载 script 标签
function loadScript(parent){
    let scripts = parent.getElementsByTagName("script");
    for(let i = 0; i < scripts.length; i++){
        let script = scripts[i];
        let parent = script.parentElement;
        let script_content = script.innerText;
        script.parentElement.removeChild(script);
        runScript(parent,script_content);
    }
}
// 重新运行 js
function runScript(parent,script){
    let script_dom =document.createElement("script");
    script_dom.type = "text/javascript";
    script_dom.text = script;
    parent.appendChild(script_dom);
}
