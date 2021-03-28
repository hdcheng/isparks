'use strict'
window.onload = function(){

    const jsLine = ["/static/js/utils.js","/static/js/md5.min.js","/static/js/vue.2.x.js","/static/js/axios.min.js"];
    
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