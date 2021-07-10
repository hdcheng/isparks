'use strict'

const Utils = {
    // 提示框封装
    popup : {
        success:function(message){
            UIkit.notification(message, "success");
        },
        error:function(message){
            UIkit.notification(message, "danger");
        },
        info:function(message){
            UIkit.notification(message, "primary");
        }
    },
    http : {
        post(url,data,config){
            const rep = this.response();
            axios.post(url,data).then(res => {
                rep.result = res;
                rep.status = res.status;
            }).catch(err => {
                rep.error = err;
                rep.status = err.status;
            });
                    
            return rep;
        },
        get(url,params,config){


        },
        response(){

            return {
                status : null,
                result : null,
                error : null,
                then : function(callback){
                    
                    
                    console.log(this.result);
                    console.log(this);

                    if(this.result != null){
                        callback(this.result);
                    }
                    return this;
                },
                catch : function(callback){

                    console.log(this.error);
                    if(this.error != null){
                        callback(this.error);
                    }
                }
            }
        }
    },
    auth :{
        getToken(){
            let token = Utils.cookies.get("authorization");
            if(token == null){
                token = localStorage.getItem("authorization")
            }
            return token ;
        },
        setToken(token){
            if(Utils.client.cookieEnabled){
                Utils.cookies.set("authorization",token,8*60*60*1000);
            }else{
                localStorage.setItem("authorization",token);
            }
        },
        getClaims(){ // 获取jwt中的数据
            const jwt = this.getToken();
            if(jwt){
                let parts = jwt.split(".");
                let payload = parts[1];
                payload = payload.replace(/-/g,'+').replace(/_/g,'/');
                return JSON.parse(window.atob(payload));
            }else{
                return {};
            }
        },
        getInfoFromToken(key){
            return this.getClaims()[key];
        },
        removeToken(){
            if(Utils.client.cookieEnabled){
                Utils.cookies.delete("authorization");
            }
            localStorage.removeItem("authorization");
            return true;
        }
    },
    // cookie utils
    cookies:{
        get(name){
            if(document.cookie.length>0){
                let start=document.cookie.indexOf(name+"=");
                if(start!=-1){
                    start=start+name.length+1;
                    let end=document.cookie.indexOf(";", start);
                     
                    if(end==-1){
                        end=document.cookie.length;
                    }
                    return unescape(document.cookie.substring(start,end));
                     
                }
            }
        },
        set(name , value , expiresTime){

            expiresTime = expiresTime || 8*60*60*1000;  // 默认保存8小时

            const expires=new Date();
            expires.setTime(expires.getTime()+expiresTime)
            document.cookie=name+"="+escape(value)+((expires==null)?"":";expires="+expires.toGMTString());
        },
        delete(name){
            var exp=new Date();
            exp.setTime(exp.getTime(-1));
            var cval=this.get(name);
            if(cval!=null){
                document.cookie = name+"="+cval+";expires="+exp.toGMTString();
            }
        }

    },
    // result utils
    result:{
        isSuccess(res){
            return res.code == "8101";
        },
        isFail(res){
            return res.code == "8101";
        },
        isError(res){
            return res.code == "8103";
        }
    },
    // client utils
    client:{
        fingerprint(){ // browser fingerprint 浏览器指纹信息
            if(this.isHeadless()){
                return "";
            }
            return md5(JSON.stringify(this.info()));
        },
        info(){ // 获取客户端信息
            return {
                os : this.os(),
                browser : this.browser(),
                ip : this.ip(),
                screen : this.screen(),
                lan : this.language(),
                canf : this.canvasFingerprint(),
                timeZone : this.timeZone()
            }
        },
        browser(){ // 浏览器信息，如Chrome on Windows (10)
            const userAgent = navigator.userAgent;
            let browser = "Unknown";
            if(userAgent.indexOf("Opera") > -1 || userAgent.indexOf("OPR") > -1){
                browser = 'Opera';
            }else if(userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1){
                browser = 'IE';
            }else if(userAgent.indexOf("Edge") > -1){
                browser = 'Edge';
            }else if(userAgent.indexOf("Firefox") > -1){
                browser = 'Firefox';
            }else if(userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") == -1){
                browser = 'Safari';
            }else if(userAgent.indexOf("Chrome") > -1 && userAgent.indexOf("Safari") > -1){
                browser = 'Chrome';
            }else if(!!window.ActiveXObject || "ActiveXObject" in window){
                browser = 'IE>=11';
            }
            return browser + " on " + this.os();
            //return navigator.appName + " on " + this.os();
        },
        language(){// 浏览器语言信息
            let result = navigator.language + " in ";
            const ls = navigator.languages;
            result += "[ "
            for(var i in navigator.languages){
                result += (ls[i]+ " ");
            }
            result += "]"
            return result;
        },
        cookieEnabled(){ // 是否启用了cookies
            return navigator.cookieEnabled;
        },
        isHeadless(){ // 检测是否是无头浏览器
            // 检测 user agent
            if (/HeadlessChrome/.test(window.navigator.userAgent)) {
                console.log("Chrome headless detected");
                return true;
            }
            
            // headless 的浏览器语言为 null
            if(navigator.languages == "") {
                console.log("Chrome headless detected");
                return true;
            }

            return false;
        },
        ip(){ // ip 信息
            let ip = localStorage.getItem("ip");

            if(ip != null){
                return ip;
            }

            const url = "http://httpbin.org/get";
            let res = null;
            const request = new XMLHttpRequest();
            request.open('GET', url, false); // 同步
            request.setRequestHeader("Content-Type","application/x-www-form-urlencoded;"); 
            request.onreadystatechange = function () {
                if (request.readyState == 4 && request.status == 200) {
                    res = JSON.parse(request.responseText);
                }
            };
            request.send();
            if(res != null){
                ip = res.origin;

                localStorage.setItem("ip",ip);

                return ip;
            }else{
                return "unknown";
            }
        },
        os(){ // 运行的系统

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
            return  name + "(" + version + ")" ;
            //return navigator.platform;
        },
        screen(){ // 屏幕信息
            return window.screen.width + " x " + window.screen.height + " x " + window.screen.colorDepth ;
        },
        canvasFingerprint(){ // 生成canvas指纹信息
            const canvas = document.createElement('canvas');
            canvas.style.display = "none";
            document.body.appendChild(canvas);
            const context = canvas.getContext("2d");
            context.font = "18pt Arial";
            context.textBaseline = "top";
            context.fillText("ISparks.", 2, 2);
            const b64 = canvas.toDataURL("image/png").replace("data:image/png;base64,","");
            return md5(b64);
        },
        timeZone(){ // 时区
            const tz = new Date().getTimezoneOffset()/60;
            return tz;
        }

    }
}

