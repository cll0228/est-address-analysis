var lianjiaCasManager = function() {
    var t = {};
    t.joinUrl = function(e, n, r) {
        if (e || (e = location.href), !n) return e;
        var i = t.parseURL(e),
        a = [];
        for (var o in n) o && a.push(o + "=" + encodeURIComponent(n[o]));
        var s = i.query ? i.query + "&" + a.join("&") : a.join("&");
        s && (s = "?" + s);
        var u = i.path;
        u && (u = "/" + u);
        var c = r ? "": i.hash ? "#" + i.hash: "";
        return i.origin + u + s + c
    },
    t.parseURL = function(t) {
        if (!t) return null;
        var e = /^((?:([A-Za-z]+):(\/{0,3}))?([0-9.\-A-Za-z]+\.[0-9A-Za-z]+)?(?::(\d+))?)(?:\/([^?#]*))?(?:\?([^#]*))?(?:#(.*))?$/,
        n = ["url", "origin", "scheme", "slash", "host", "port", "path", "query", "hash"],
        r = e.exec(t),
        i = {};
        if (r) for (var a = 0,
        o = n.length; o > a; a += 1) i[n[a]] = r[a] || "";
        return i
    },
    t.trim = function(t) {
        return t ? t.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, "") : t
    },
    t.extend = function(t, e) {
        if (e) {
            for (var n in e) t[n] = e[n];
            return t
        }
    },
    t.Deferred = function() {
        return function() {
            var t, e = {},
            n = [],
            r = [],
            i = "init";
            return e.done = function(r) {
                return "success" == i ? r(t) : "init" == i && n.push(r),
                e
            },
            e.fail = function(n) {
                return "fail" == i ? n(t) : "init" == i && r.push(n),
                e
            },
            e.then = e.done,
            e.resolve = function(r) {
                i = "success",
                t = r;
                for (var a = !1; a = n.shift();) a(t);
                return e
            },
            e.reject = function(e) {
                i = "fail",
                t = e;
                for (var n = !1; n = r.shift();) n()
            },
            e
        }
    } ();
    var e = {
        getFirstTicket: "https://passport.lianjia.com/cas/prelogin/loginTicket?v="+Number(new Date()),//北京那边好像做了缓存的，相同请求第二次就不再发了，所以加一个时间戳
        loginUrl: "https://passport.lianjia.com/cas/login",
        service: "http://sh.lianjia.com",
        setLoginUrl: headerParameters.uchost + "/login/getLoginUserInfo.json"
    },
    n = function() {
        var e = location.href,
        n = t.parseURL(e);
        return !1
    } ();
    var r = {};
    return r.config = function(n) {
        t.extend(e, n)
    },
    window.Messenger = function() {
        function t(t, e) {
            var n = "";
            if (arguments.length < 2 ? n = "target error - target and name are both requied": "object" != typeof t ? n = "target error - target itself must be window object": "string" != typeof e && (n = "target error - target name must be string type"), n) throw new Error(n);
            this.target = t,
            this.name = e
        }
        function e(t, e) {
            this.targets = {},
            this.name = t,
            this.listenFunc = [],
            n = e || n,
            "string" != typeof n && (n = n.toString()),
            this.initListen()
        }
        var n = "[LIANJIA_CROSS]",
        r = "postMessage" in window;
        return t.prototype.send = r ?
        function(t) {
            this.target.postMessage(n + t, "*")
        }: function(t) {
            var e = window.navigator[n + this.name];
            if ("function" != typeof e) throw new Error("target callback function is not defined");
            e(n + t, window)
        },
        e.prototype.addTarget = function(e, n) {
            var r = new t(e, n);
            this.targets[n] = r
        },
        e.prototype.initListen = function() {
            var t = this,
            e = function(e) {
                "object" == typeof e && e.data && (e = e.data),
                e = e.slice(n.length);
                for (var r = 0; r < t.listenFunc.length; r++) t.listenFunc[r](e)
            };
            r ? "addEventListener" in document ? window.addEventListener("message", e, !1) : "attachEvent" in document && window.attachEvent("onmessage", e) : window.navigator[n + this.name] = e
        },
        e.prototype.listen = function(t) {
            this.listenFunc.push(t)
        },
        e.prototype.clear = function() {
            this.listenFunc = []
        },
        e.prototype.send = function(t) {
            var e, n = this.targets;
            for (e in n) n.hasOwnProperty(e) && n[e].send(t)
        },
        e
    } (),
    t.crossRequest = function() {
        var e = new Messenger("LIANJIA_CROSS_MESSAGE", "LIANJIA-CROSS");
        e.listen(function(t) {
            t = JSON.parse(t);
            var n = t.name;
            e.targets[n] && ("state" == t.type ? (e.targets[n].readyState = "ready", e.targets[n].dealReady()) : e.targets[n].deal(t.data, t.success))
        });
        var n = {},
        r = function(e, n) {
            var r = this;
            r.domain = e,
            n = n || t.parseURL(e).host.replace(/\./g, "-"),
            r.name = n,
            r.init()
        };
        return t.extend(r.prototype, {
            init: function() {
                var t = this,
                n = t.domain + "/cas/xd/api?name=" + t.name,
                r = document.createElement("iframe");
                r.id = t.name,
                r.tabindex = "-1",
                r.src = n,
                r.style.display = "none",
                r.width = 0,
                r.height = 0,
                r.title = "empty",
                document.body.appendChild(r),
                t.iframe = r.contentWindow,
                e.addTarget(t.iframe, t.name),
                t.reqArray = [],
                e.targets[t.name].deal = function(n, r) {
                    e.targets[t.name].isRequest = !1;
                    var i = t.reqArray.shift(),
                    a = !1;
                    try {
                        a = n
                    } catch(o) {}
                    r ? i.defer.resolve(a) : i.defer.reject(a),
                    t.next()
                },
                e.targets[t.name].dealReady = function() {
                    t.next()
                }
            },
            next: function() {
                var t = this;
                if (e.targets[t.name].readyState && t.reqArray.length && !e.targets[t.name].isRequest) {
                    e.targets[t.name].isRequest = !0;
                    var n = t.reqArray[0],
                    r = {
                        type: "request",
                        data: n.request
                    },
                    i = JSON.stringify(r);
                    e.targets[t.name].send(i)
                }
            },
            request: function(e) {
                var n = this,
                r = t.Deferred();
                return n.reqArray.push({
                    defer: r,
                    request: e
                }),
                n.next(),
                r
            }
        }),
        function(t, e) {
            return n[t] ? n[t] : n[t] = new r(t, e)
        }
    } (),
    t.XDRequest = function() {
        var e = t.crossRequest,
        n = function(t) {
            var n = e(t);
            return function(t) {
                return n.request(t)
            }
        };
        return n
    } (),
    t.Trans = function() {
        function e(r) {
            if (! (this instanceof e)) return new e(r);
            var i = this,
            a = {
                url: "",
                method: "get",
                dataType: "json",
                data: {},
                timeout: 3e4
            };
            i.opt = t.extend(a, r);
            var o = t.parseURL(i.opt.url);
            i.trans = n(o.origin)
        }
        var n = (t.parseURL(location.href).origin, t.XDRequest);
        return t.extend(e.prototype, {
            request: function(e, n) {
                var r = {
                    success: t.noop,
                    fail: t.noop,
                    timeout: t.noop,
                    timeout: 15e3
                };
                t.extend(r, n);
                var i = t.Deferred(),
                a = this;
                t.extend(a.opt.data, e);
                a.trans({
                    url: a.opt.url,
                    type: a.opt.type,
                    traditional: !0,
                    data: a.opt.data,
                    timeout: a.opt.timeout
                }).done(function(t) {
                    i.resolve(t)
                }).fail(function(t) {
                    i.reject(t)
                });
                return i
            },
            setArgs: function(e) {
                t.extend(this.opt.data, e)
            },
            resetUrl: function(t) {
                this.opt.url = t
            },
            clearArgs: function(t) {
                if (t) for (var e in t) this.opt.data[e] && delete this.opt.data[e];
                else this.opt.data = {}
            }
        }),
        e
    } (),
    t.jsonp = function() {
        function e() {
            if(!checkBrowserIsLegal()){
                return "casbacklogininfo";//delete window[c] ie下会报错，所以采用window[c] = null;保证生成出来的名字是唯一的 所以索性写死
            }
            return "casback" + (new Date).getTime() + n++
        }
        var n = 0;
        return function(n) {
            function r(t) {
                s.success && s.success(t)
            }
            function i() {
                f || (f = !0, o(), s.fail && s.fail()),
                s.fail && s.fail()
            }
            function a() {
                f || (f = !0, o()),
                s.timeout && s.timeout()
            }
            function o() {
                if(!checkBrowserIsLegal()){//ie8 一下不支持delete 所以用置为null的
                    window[c] = null;
                }else{
                    delete window[c];
                }

                p.removeChild(l),
                clearTimeout(u)
            }
            var s = {
                url: "",
                data: {},
                success: function() {},
                fail: function() {},
                timeout: 3e4
            };
            t.extend(s, n),
            s.data || (s.data = {});
            var u = !1,
            c = e();
            s.data.callback = c;
            var f = !1,
            d = t.joinUrl(s.url, s.data),
            l = document.createElement("script");
            l.type = "text/javascript",
            l.charset = "utf-8",
            l.src = d;
            var p = document.getElementsByTagName("head")[0];
            p.appendChild(l),
            u = setTimeout(function() {
                a()
            },
            s.timeout),
            l.onerror = function() {
                i()
            },
            window[c] = function(t) {
                f || (f = !0, o(), r(t))
            }
        }
    } (),
    r.login = function(n, r, i) {
        function a(n, r) {
            var i = new t.Trans({
                url: e.getFirstTicket
            });
            i.request().then(function(t) {
                n(t)
            }).fail(function(t) {
                r && r(t)
            })
        }
        function o(n, r, i) {
            var a = new t.Trans({
                url: e.loginUrl,
                type: "post",
                dataType: "html"
            });
            a.request(n).then(function(t) {
                r(t)
            }).fail(function() {
                i && i()
            })
        }
        function s(n, r, i) {
            t.jsonp({
                url: e.setLoginUrl,
                data: n,
                success: function(t) {
                    r && r(t)
                },
                fail: function() {
                    i && i()
                }
            })
        }
        var u = {
            username: "",
            password: "",
            verifycode: "",
            service: e.service,
            isajax: !0
        };
        t.extend(u, n),
        a(function(t) {
            var e = t;
            ticket = e.data,
            u.lt = ticket,
            u.service || (u.service = location.href),
            o(u,
            function(t) {
                t.success ? s({
                    service: u.service,
                    ticket: t.ticket
                },
                r, i) : i(t)
            },
            i)
        },
        i)
    },
    r
} ();