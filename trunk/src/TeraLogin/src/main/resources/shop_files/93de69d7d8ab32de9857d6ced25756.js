!function(b){b(function(){b.support.transition=function(){var c=function(){var e=document.createElement("bootstrap"),d={WebkitTransition:"webkitTransitionEnd",MozTransition:"transitionend",OTransition:"oTransitionEnd otransitionend",transition:"transitionend"},f;for(f in d){if(e.style[f]!==undefined){return d[f]}}}();return c&&{end:c}}()})}(window.jQuery),!function(e){var d=function(a,g){this.options=g,this.$element=e(a).delegate('[data-dismiss="modal"]',"click.dismiss.modal",e.proxy(this.hide,this)),this.options.remote&&this.$element.find(".modal-body").load(this.options.remote)};d.prototype={constructor:d,toggle:function(){return this[this.isShown?"hide":"show"]()},show:function(){var a=this,g=e.Event("show");this.$element.trigger(g);if(this.isShown||g.isDefaultPrevented()){return}this.isShown=!0,this.escape(),this.backdrop(function(){var b=e.support.transition&&a.$element.hasClass("fade");a.$element.parent().length||a.$element.appendTo(document.body),a.$element.show(),b&&a.$element[0].offsetWidth,a.$element.addClass("in").attr("aria-hidden",!1),a.enforceFocus(),b?a.$element.one(e.support.transition.end,function(){a.$element.focus().trigger("shown")}):a.$element.focus().trigger("shown")})},hide:function(a){a&&a.preventDefault();var g=this;a=e.Event("hide"),this.$element.trigger(a);if(!this.isShown||a.isDefaultPrevented()){return}this.isShown=!1,this.escape(),e(document).off("focusin.modal"),this.$element.removeClass("in").attr("aria-hidden",!0),e.support.transition&&this.$element.hasClass("fade")?this.hideWithTransition():this.hideModal()},enforceFocus:function(){var a=this;e(document).on("focusin.modal",function(b){a.$element[0]!==b.target&&!a.$element.has(b.target).length&&a.$element.focus()})},escape:function(){var b=this;this.isShown&&this.options.keyboard?this.$element.on("keyup.dismiss.modal",function(a){a.which==27&&b.hide()}):this.isShown||this.$element.off("keyup.dismiss.modal")},hideWithTransition:function(){var a=this,g=setTimeout(function(){a.$element.off(e.support.transition.end),a.hideModal()},500);this.$element.one(e.support.transition.end,function(){clearTimeout(g),a.hideModal()})},hideModal:function(){var b=this;this.$element.hide(),this.backdrop(function(){b.removeBackdrop(),b.$element.trigger("hidden")})},removeBackdrop:function(){this.$backdrop&&this.$backdrop.remove(),this.$backdrop=null},backdrop:function(a){var i=this,h=this.$element.hasClass("fade")?"fade":"";if(this.isShown&&this.options.backdrop){var g=e.support.transition&&h;this.$backdrop=e('<div class="modal-backdrop '+h+'" />').appendTo(document.body),this.$backdrop.click(this.options.backdrop=="static"?e.proxy(this.$element[0].focus,this.$element[0]):e.proxy(this.hide,this)),g&&this.$backdrop[0].offsetWidth,this.$backdrop.addClass("in");if(!a){return}g?this.$backdrop.one(e.support.transition.end,a):a()}else{!this.isShown&&this.$backdrop?(this.$backdrop.removeClass("in"),e.support.transition&&this.$element.hasClass("fade")?this.$backdrop.one(e.support.transition.end,a):a()):a&&a()}}};var f=e.fn.modal;e.fn.modal=function(a){return this.each(function(){var g=e(this),c=g.data("modal"),b=e.extend({},e.fn.modal.defaults,g.data(),typeof a=="object"&&a);c||g.data("modal",c=new d(this,b)),typeof a=="string"?c[a]():b.show&&c.show()})},e.fn.modal.defaults={backdrop:!0,keyboard:!0,show:!0},e.fn.modal.Constructor=d,e.fn.modal.noConflict=function(){return e.fn.modal=f,this},e(document).on("click.modal.data-api",'[data-toggle="modal"]',function(a){var j=e(this),i=j.attr("href"),h=e(j.attr("data-target")||i&&i.replace(/.*(?=#[^\s]+$)/,"")),g=h.data("modal")?"toggle":e.extend({remote:!/#/.test(i)&&i},h.data(),j.data());a.preventDefault(),h.modal(g).one("hide",function(){j.focus()})})}(window.jQuery),!function(h){function k(){h(".dropdown-backdrop").remove(),h(g).each(function(){j(h(this)).removeClass("open")})}function j(a){var f=a.attr("data-target"),e;f||(f=a.attr("href"),f=f&&/#/.test(f)&&f.replace(/.*(?=#[^\s]*$)/,"")),e=f&&h(f);if(!e||!e.length){e=a.parent()}return e}var g="[data-toggle=dropdown]",l=function(a){var d=h(a).on("click.dropdown.data-api",this.toggle);h("html").on("click.dropdown.data-api",function(){d.parent().removeClass("open")})};l.prototype={constructor:l,toggle:function(a){var m=h(this),e,d;if(m.is(".disabled, :disabled")){return}return e=j(m),d=e.hasClass("open"),k(),d||("ontouchstart" in document.documentElement&&h('<div class="dropdown-backdrop"/>').insertBefore(h(this)).on("click",k),e.toggleClass("open")),m.focus(),!1},keydown:function(p){var o,n,m,e,b,a;if(!/(38|40|27)/.test(p.keyCode)){return}o=h(this),p.preventDefault(),p.stopPropagation();if(o.is(".disabled, :disabled")){return}e=j(o),b=e.hasClass("open");if(!b||b&&p.keyCode==27){return p.which==27&&e.find(g).focus(),o.click()}n=h("[role=menu] li:not(.divider):visible a",e);if(!n.length){return}a=n.index(n.filter(":focus")),p.keyCode==38&&a>0&&a--,p.keyCode==40&&a<n.length-1&&a++,~a||(a=0),n.eq(a).focus()}};var i=h.fn.dropdown;h.fn.dropdown=function(a){return this.each(function(){var c=h(this),b=c.data("dropdown");b||c.data("dropdown",b=new l(this)),typeof a=="string"&&b[a].call(c)})},h.fn.dropdown.Constructor=l,h.fn.dropdown.noConflict=function(){return h.fn.dropdown=i,this},h(document).on("click.dropdown.data-api",k).on("click.dropdown.data-api",".dropdown form",function(b){b.stopPropagation()}).on("click.dropdown.data-api",g,l.prototype.toggle).on("keydown.dropdown.data-api",g+", [role=menu]",l.prototype.keydown)}(window.jQuery),!function(e){function d(a,j){var i=e.proxy(this.process,this),h=e(a).is("body")?e(window):e(a),g;this.options=e.extend({},e.fn.scrollspy.defaults,j),this.$scrollElement=h.on("scroll.scroll-spy.data-api",i),this.selector=(this.options.target||(g=e(a).attr("href"))&&g.replace(/.*(?=#[^\s]+$)/,"")||"")+" .nav li > a",this.$body=e("body"),this.refresh(),this.process()}d.prototype={constructor:d,refresh:function(){var a=this,g;this.offsets=e([]),this.targets=e([]),g=this.$body.find(this.selector).map(function(){var i=e(this),h=i.data("target")||i.attr("href"),b=/^#\w/.test(h)&&e(h);return b&&b.length&&[[b.position().top+(!e.isWindow(a.$scrollElement.get(0))&&a.$scrollElement.scrollTop()),h]]||null}).sort(function(h,c){return h[0]-c[0]}).each(function(){a.offsets.push(this[0]),a.targets.push(this[1])})},process:function(){var i=this.$scrollElement.scrollTop()+this.options.offset,h=this.$scrollElement[0].scrollHeight||this.$body[0].scrollHeight,n=h-this.$scrollElement.height(),m=this.offsets,l=this.targets,k=this.activeTarget,j;if(i>=n){return k!=(j=l.last()[0])&&this.activate(j)}for(j=m.length;j--;){k!=l[j]&&i>=m[j]&&(!m[j+1]||i<=m[j+1])&&this.activate(l[j])}},activate:function(a){var h,g;this.activeTarget=a,e(this.selector).parent(".active").removeClass("active"),g=this.selector+'[data-target="'+a+'"],'+this.selector+'[href="'+a+'"]',h=e(g).parent("li").addClass("active"),h.parent(".dropdown-menu").length&&(h=h.closest("li.dropdown").addClass("active")),h.trigger("activate")}};var f=e.fn.scrollspy;e.fn.scrollspy=function(a){return this.each(function(){var g=e(this),c=g.data("scrollspy"),b=typeof a=="object"&&a;c||g.data("scrollspy",c=new d(this,b)),typeof a=="string"&&c[a]()})},e.fn.scrollspy.Constructor=d,e.fn.scrollspy.defaults={offset:10},e.fn.scrollspy.noConflict=function(){return e.fn.scrollspy=f,this},e(window).on("load",function(){e('[data-spy="scroll"]').each(function(){var a=e(this);a.scrollspy(a.data())})})}(window.jQuery),!function(e){var d=function(a){this.element=e(a)};d.prototype={constructor:d,show:function(){var a=this.element,l=a.closest("ul:not(.dropdown-menu)"),k=a.attr("data-target"),j,i,h;k||(k=a.attr("href"),k=k&&k.replace(/.*(?=#[^\s]*$)/,""));if(a.parent("li").hasClass("active")){return}j=l.find(".active:last a")[0],h=e.Event("show",{relatedTarget:j}),a.trigger(h);if(h.isDefaultPrevented()){return}i=e(k),this.activate(a.parent("li"),l),this.activate(i,i.parent(),function(){a.trigger({type:"shown",relatedTarget:j})})},activate:function(a,l,k){function h(){j.removeClass("active").find("> .dropdown-menu > .active").removeClass("active"),a.addClass("active"),i?(a[0].offsetWidth,a.addClass("in")):a.removeClass("fade"),a.parent(".dropdown-menu")&&a.closest("li.dropdown").addClass("active"),k&&k()}var j=l.find("> .active"),i=k&&e.support.transition&&j.hasClass("fade");i?j.one(e.support.transition.end,h):h(),j.removeClass("in")}};var f=e.fn.tab;e.fn.tab=function(a){return this.each(function(){var c=e(this),b=c.data("tab");b||c.data("tab",b=new d(this)),typeof a=="string"&&b[a]()})},e.fn.tab.Constructor=d,e.fn.tab.noConflict=function(){return e.fn.tab=f,this},e(document).on("click.tab.data-api",'[data-toggle="tab"], [data-toggle="pill"]',function(a){a.preventDefault(),e(this).tab("show")})}(window.jQuery),!function(e){var d=function(g,c){this.init("tooltip",g,c)};d.prototype={constructor:d,init:function(a,p,o){var n,m,l,k,j;this.type=a,this.$element=e(p),this.options=this.getOptions(o),this.enabled=!0,l=this.options.trigger.split(" ");for(j=l.length;j--;){k=l[j],k=="click"?this.$element.on("click."+this.type,this.options.selector,e.proxy(this.toggle,this)):k!="manual"&&(n=k=="hover"?"mouseenter":"focus",m=k=="hover"?"mouseleave":"blur",this.$element.on(n+"."+this.type,this.options.selector,e.proxy(this.enter,this)),this.$element.on(m+"."+this.type,this.options.selector,e.proxy(this.leave,this)))}this.options.selector?this._options=e.extend({},this.options,{trigger:"manual",selector:""}):this.fixTitle()},getOptions:function(a){return a=e.extend({},e.fn[this.type].defaults,this.$element.data(),a),a.delay&&typeof a.delay=="number"&&(a.delay={show:a.delay,hide:a.delay}),a},enter:function(a){var i=e.fn[this.type].defaults,h={},g;this._options&&e.each(this._options,function(j,c){i[j]!=c&&(h[j]=c)},this),g=e(a.currentTarget)[this.type](h).data(this.type);if(!g.options.delay||!g.options.delay.show){return g.show()}clearTimeout(this.timeout),g.hoverState="in",this.timeout=setTimeout(function(){g.hoverState=="in"&&g.show()},g.options.delay.show)},leave:function(a){var g=e(a.currentTarget)[this.type](this._options).data(this.type);this.timeout&&clearTimeout(this.timeout);if(!g.options.delay||!g.options.delay.hide){return g.hide()}g.hoverState="out",this.timeout=setTimeout(function(){g.hoverState=="out"&&g.hide()},g.options.delay.hide)},show:function(){var a,n,m,l,k,j,i=e.Event("show");if(this.hasContent()&&this.enabled){this.$element.trigger(i);if(i.isDefaultPrevented()){return}a=this.tip(),this.setContent(),this.options.animation&&a.addClass("fade"),k=typeof this.options.placement=="function"?this.options.placement.call(this,a[0],this.$element[0]):this.options.placement,a.detach().css({top:0,left:0,display:"block"}),this.options.container?a.appendTo(this.options.container):a.insertAfter(this.$element),n=this.getPosition(),m=a[0].offsetWidth,l=a[0].offsetHeight;switch(k){case"bottom":j={top:n.top+n.height,left:n.left+n.width/2-m/2};break;case"top":j={top:n.top-l,left:n.left+n.width/2-m/2};break;case"left":j={top:n.top+n.height/2-l/2,left:n.left-m};break;case"right":j={top:n.top+n.height/2-l/2,left:n.left+n.width}}this.applyPlacement(j,k),this.$element.trigger("shown")}},applyPlacement:function(r,q){var p=this.tip(),o=p[0].offsetWidth,n=p[0].offsetHeight,m,l,k,j;p.offset(r).addClass(q).addClass("in"),m=p[0].offsetWidth,l=p[0].offsetHeight,q=="top"&&l!=n&&(r.top=r.top+n-l,j=!0),q=="bottom"||q=="top"?(k=0,r.left<0&&(k=r.left*-2,r.left=0,p.offset(r),m=p[0].offsetWidth,l=p[0].offsetHeight),this.replaceArrow(k-o+m,m,"left")):this.replaceArrow(l-n,l,"top"),j&&p.offset(r)},replaceArrow:function(h,g,i){this.arrow().css(i,h?50*(1-h/g)+"%":"")},setContent:function(){var g=this.tip(),c=this.getTitle();g.find(".tooltip-inner")[this.options.html?"html":"text"](c),g.removeClass("fade in top bottom left right")},hide:function(){function g(){var c=setTimeout(function(){i.off(e.support.transition.end).detach()},500);i.one(e.support.transition.end,function(){clearTimeout(c),i.detach()})}var a=this,i=this.tip(),h=e.Event("hide");this.$element.trigger(h);if(h.isDefaultPrevented()){return}return i.removeClass("in"),e.support.transition&&this.$tip.hasClass("fade")?g():i.detach(),this.$element.trigger("hidden"),this},fixTitle:function(){var b=this.$element;(b.attr("title")||typeof b.attr("data-original-title")!="string")&&b.attr("data-original-title",b.attr("title")||"").attr("title","")},hasContent:function(){return this.getTitle()},getPosition:function(){var a=this.$element[0];return e.extend({},typeof a.getBoundingClientRect=="function"?a.getBoundingClientRect():{width:a.offsetWidth,height:a.offsetHeight},this.$element.offset())},getTitle:function(){var h,g=this.$element,i=this.options;return h=g.attr("data-original-title")||(typeof i.title=="function"?i.title.call(g[0]):i.title),h},tip:function(){return this.$tip=this.$tip||e(this.options.template)},arrow:function(){return this.$arrow=this.$arrow||this.tip().find(".tooltip-arrow")},validate:function(){this.$element[0].parentNode||(this.hide(),this.$element=null,this.options=null)},enable:function(){this.enabled=!0},disable:function(){this.enabled=!1},toggleEnabled:function(){this.enabled=!this.enabled},toggle:function(a){var g=a?e(a.currentTarget)[this.type](this._options).data(this.type):this;g.tip().hasClass("in")?g.hide():g.show()},destroy:function(){this.hide().$element.off("."+this.type).removeData(this.type)}};var f=e.fn.tooltip;e.fn.tooltip=function(a){return this.each(function(){var g=e(this),c=g.data("tooltip"),b=typeof a=="object"&&a;c||g.data("tooltip",c=new d(this,b)),typeof a=="string"&&c[a]()})},e.fn.tooltip.Constructor=d,e.fn.tooltip.defaults={animation:!0,placement:"top",selector:!1,template:'<div class="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>',trigger:"hover focus",title:"",delay:0,html:!1,container:!1},e.fn.tooltip.noConflict=function(){return e.fn.tooltip=f,this}}(window.jQuery),!function(e){var d=function(g,c){this.init("popover",g,c)};d.prototype=e.extend({},e.fn.tooltip.Constructor.prototype,{constructor:d,setContent:function(){var h=this.tip(),g=this.getTitle(),i=this.getContent();h.find(".popover-title")[this.options.html?"html":"text"](g),h.find(".popover-content")[this.options.html?"html":"text"](i),h.removeClass("fade top bottom left right in")},hasContent:function(){return this.getTitle()||this.getContent()},getContent:function(){var h,g=this.$element,i=this.options;return h=(typeof i.content=="function"?i.content.call(g[0]):i.content)||g.attr("data-content"),h},tip:function(){return this.$tip||(this.$tip=e(this.options.template)),this.$tip},destroy:function(){this.hide().$element.off("."+this.type).removeData(this.type)}});var f=e.fn.popover;e.fn.popover=function(a){return this.each(function(){var g=e(this),c=g.data("popover"),b=typeof a=="object"&&a;c||g.data("popover",c=new d(this,b)),typeof a=="string"&&c[a]()})},e.fn.popover.Constructor=d,e.fn.popover.defaults=e.extend({},e.fn.tooltip.defaults,{placement:"right",trigger:"click",content:"",template:'<div class="popover"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'}),e.fn.popover.noConflict=function(){return e.fn.popover=f,this}}(window.jQuery),!function(e){var d=function(a,g){this.options=e.extend({},e.fn.affix.defaults,g),this.$window=e(window).on("scroll.affix.data-api",e.proxy(this.checkPosition,this)).on("click.affix.data-api",e.proxy(function(){setTimeout(e.proxy(this.checkPosition,this),1)},this)),this.$element=e(a),this.checkPosition()};d.prototype.checkPosition=function(){if(!this.$element.is(":visible")){return}var a=e(document).height(),p=this.$window.scrollTop(),o=this.$element.offset(),n=this.options.offset,m=n.bottom,l=n.top,k="affix affix-top affix-bottom",j;typeof n!="object"&&(m=l=n),typeof l=="function"&&(l=n.top()),typeof m=="function"&&(m=n.bottom()),j=this.unpin!=null&&p+this.unpin<=o.top?!1:m!=null&&o.top+this.$element.height()>=a-m?"bottom":l!=null&&p<=l?"top":!1;if(this.affixed===j){return}this.affixed=j,this.unpin=j=="bottom"?o.top-p:null,this.$element.removeClass(k).addClass("affix"+(j?"-"+j:""))};var f=e.fn.affix;e.fn.affix=function(a){return this.each(function(){var g=e(this),c=g.data("affix"),b=typeof a=="object"&&a;c||g.data("affix",c=new d(this,b)),typeof a=="string"&&c[a]()})},e.fn.affix.Constructor=d,e.fn.affix.defaults={offset:0},e.fn.affix.noConflict=function(){return e.fn.affix=f,this},e(window).on("load",function(){e('[data-spy="affix"]').each(function(){var a=e(this),g=a.data();g.offset=g.offset||{},g.offsetBottom&&(g.offset.bottom=g.offsetBottom),g.offsetTop&&(g.offset.top=g.offsetTop),a.affix(g)})})}(window.jQuery),!function(f){var e='[data-dismiss="alert"]',h=function(a){f(a).on("click",e,this.close)};h.prototype.close=function(a){function i(){j.trigger("closed").remove()}var l=f(this),k=l.attr("data-target"),j;k||(k=l.attr("href"),k=k&&k.replace(/.*(?=#[^\s]*$)/,"")),j=f(k),a&&a.preventDefault(),j.length||(j=l.hasClass("alert")?l:l.parent()),j.trigger(a=f.Event("close"));if(a.isDefaultPrevented()){return}j.removeClass("in"),f.support.transition&&j.hasClass("fade")?j.on(f.support.transition.end,i):i()};var g=f.fn.alert;f.fn.alert=function(a){return this.each(function(){var c=f(this),b=c.data("alert");b||c.data("alert",b=new h(this)),typeof a=="string"&&b[a].call(c)})},f.fn.alert.Constructor=h,f.fn.alert.noConflict=function(){return f.fn.alert=g,this},f(document).on("click.alert.data-api",e,h.prototype.close)}(window.jQuery),!function(e){var d=function(a,g){this.$element=e(a),this.options=e.extend({},e.fn.button.defaults,g)};d.prototype.setState=function(h){var g="disabled",k=this.$element,j=k.data(),i=k.is("input")?"val":"html";h+="Text",j.resetText||k.data("resetText",k[i]()),k[i](j[h]||this.options[h]),setTimeout(function(){h=="loadingText"?k.addClass(g).attr(g,g):k.removeClass(g).removeAttr(g)},0)},d.prototype.toggle=function(){var b=this.$element.closest('[data-toggle="buttons-radio"]');b&&b.find(".active").removeClass("active"),this.$element.toggleClass("active")};var f=e.fn.button;e.fn.button=function(a){return this.each(function(){var g=e(this),c=g.data("button"),b=typeof a=="object"&&a;c||g.data("button",c=new d(this,b)),a=="toggle"?c.toggle():a&&c.setState(a)})},e.fn.button.defaults={loadingText:"loading..."},e.fn.button.Constructor=d,e.fn.button.noConflict=function(){return e.fn.button=f,this},e(document).on("click.button.data-api","[data-toggle^=button]",function(a){var g=e(a.target);g.hasClass("btn")||(g=g.closest(".btn")),g.button("toggle")})}(window.jQuery),!function(e){var d=function(a,g){this.$element=e(a),this.options=e.extend({},e.fn.collapse.defaults,g),this.options.parent&&(this.$parent=e(this.options.parent)),this.options.toggle&&this.toggle()};d.prototype={constructor:d,dimension:function(){var b=this.$element.hasClass("width");return b?"width":"height"},show:function(){var a,i,h,g;if(this.transitioning||this.$element.hasClass("in")){return}a=this.dimension(),i=e.camelCase(["scroll",a].join("-")),h=this.$parent&&this.$parent.find("> .accordion-group > .in");if(h&&h.length){g=h.data("collapse");if(g&&g.transitioning){return}h.collapse("hide"),g||h.data("collapse",null)}this.$element[a](0),this.transition("addClass",e.Event("show"),"shown"),e.support.transition&&this.$element[a](this.$element[0][i])},hide:function(){var a;if(this.transitioning||!this.$element.hasClass("in")){return}a=this.dimension(),this.reset(this.$element[a]()),this.transition("removeClass",e.Event("hide"),"hidden"),this.$element[a](0)},reset:function(g){var c=this.dimension();return this.$element.removeClass("collapse")[c](g||"auto")[0].offsetWidth,this.$element[g!==null?"addClass":"removeClass"]("collapse"),this},transition:function(a,j,i){var h=this,g=function(){j.type=="show"&&h.reset(),h.transitioning=0,h.$element.trigger(i)};this.$element.trigger(j);if(j.isDefaultPrevented()){return}this.transitioning=1,this.$element[a]("in"),e.support.transition&&this.$element.hasClass("collapse")?this.$element.one(e.support.transition.end,g):g()},toggle:function(){this[this.$element.hasClass("in")?"hide":"show"]()}};var f=e.fn.collapse;e.fn.collapse=function(a){return this.each(function(){var g=e(this),c=g.data("collapse"),b=e.extend({},e.fn.collapse.defaults,g.data(),typeof a=="object"&&a);c||g.data("collapse",c=new d(this,b)),typeof a=="string"&&c[a]()})},e.fn.collapse.defaults={toggle:!0},e.fn.collapse.Constructor=d,e.fn.collapse.noConflict=function(){return e.fn.collapse=f,this},e(document).on("click.collapse.data-api","[data-toggle=collapse]",function(a){var j=e(this),i,h=j.attr("data-target")||a.preventDefault()||(i=j.attr("href"))&&i.replace(/.*(?=#[^\s]+$)/,""),g=e(h).data("collapse")?"toggle":j.data();j[e(h).hasClass("in")?"addClass":"removeClass"]("collapsed"),e(h).collapse(g)})}(window.jQuery),!function(e){var d=function(a,g){this.$element=e(a),this.$indicators=this.$element.find(".carousel-indicators"),this.options=g,this.options.pause=="hover"&&this.$element.on("mouseenter",e.proxy(this.pause,this)).on("mouseleave",e.proxy(this.cycle,this))};d.prototype={cycle:function(a){return a||(this.paused=!1),this.interval&&clearInterval(this.interval),this.options.interval&&!this.paused&&(this.interval=setInterval(e.proxy(this.next,this),this.options.interval)),this},getActiveIndex:function(){return this.$active=this.$element.find(".item.active"),this.$items=this.$active.parent().children(),this.$items.index(this.$active)},to:function(a){var h=this.getActiveIndex(),g=this;if(a>this.$items.length-1||a<0){return}return this.sliding?this.$element.one("slid",function(){g.to(a)}):h==a?this.pause().cycle():this.slide(a>h?"next":"prev",e(this.$items[a]))},pause:function(a){return a||(this.paused=!0),this.$element.find(".next, .prev").length&&e.support.transition.end&&(this.$element.trigger(e.support.transition.end),this.cycle(!0)),clearInterval(this.interval),this.interval=null,this},next:function(){if(this.sliding){return}return this.slide("next")},prev:function(){if(this.sliding){return}return this.slide("prev")},slide:function(r,q){var p=this.$element.find(".item.active"),o=q||p[r](),n=this.interval,m=r=="next"?"left":"right",l=r=="next"?"first":"last",k=this,a;this.sliding=!0,n&&this.pause(),o=o.length?o:this.$element.find(".item")[l](),a=e.Event("slide",{relatedTarget:o[0],direction:m});if(o.hasClass("active")){return}this.$indicators.length&&(this.$indicators.find(".active").removeClass("active"),this.$element.one("slid",function(){var c=e(k.$indicators.children()[k.getActiveIndex()]);c&&c.addClass("active")}));if(e.support.transition&&this.$element.hasClass("slide")){this.$element.trigger(a);if(a.isDefaultPrevented()){return}o.addClass(r),o[0].offsetWidth,p.addClass(m),o.addClass(m),this.$element.one(e.support.transition.end,function(){o.removeClass([r,m].join(" ")).addClass("active"),p.removeClass(["active",m].join(" ")),k.sliding=!1,setTimeout(function(){k.$element.trigger("slid")},0)})}else{this.$element.trigger(a);if(a.isDefaultPrevented()){return}p.removeClass("active"),o.addClass("active"),this.sliding=!1,this.$element.trigger("slid")}return n&&this.cycle(),this}};var f=e.fn.carousel;e.fn.carousel=function(a){return this.each(function(){var i=e(this),h=i.data("carousel"),c=e.extend({},e.fn.carousel.defaults,typeof a=="object"&&a),b=typeof a=="string"?a:c.slide;h||i.data("carousel",h=new d(this,c)),typeof a=="number"?h.to(a):b?h[b]():c.interval&&h.pause().cycle()})},e.fn.carousel.defaults={interval:5000,pause:"hover"},e.fn.carousel.Constructor=d,e.fn.carousel.noConflict=function(){return e.fn.carousel=f,this},e(document).on("click.carousel.data-api","[data-slide], [data-slide-to]",function(a){var l=e(this),k,j=e(l.attr("data-target")||(k=l.attr("href"))&&k.replace(/.*(?=#[^\s]+$)/,"")),i=e.extend({},j.data(),l.data()),h;j.carousel(i),(h=l.attr("data-slide-to"))&&j.data("carousel").pause().to(h).cycle(),a.preventDefault()})}(window.jQuery),!function(e){var d=function(a,g){this.$element=e(a),this.options=e.extend({},e.fn.typeahead.defaults,g),this.matcher=this.options.matcher||this.matcher,this.sorter=this.options.sorter||this.sorter,this.highlighter=this.options.highlighter||this.highlighter,this.updater=this.options.updater||this.updater,this.source=this.options.source,this.$menu=e(this.options.menu),this.shown=!1,this.listen()};d.prototype={constructor:d,select:function(){var b=this.$menu.find(".active").attr("data-value");return this.$element.val(this.updater(b)).change(),this.hide()},updater:function(b){return b},show:function(){var a=e.extend({},this.$element.position(),{height:this.$element[0].offsetHeight});return this.$menu.insertAfter(this.$element).css({top:a.top+a.height,left:a.left}).show(),this.shown=!0,this},hide:function(){return this.$menu.hide(),this.shown=!1,this},lookup:function(a){var g;return this.query=this.$element.val(),!this.query||this.query.length<this.options.minLength?this.shown?this.hide():this:(g=e.isFunction(this.source)?this.source(this.query,e.proxy(this.process,this)):this.source,g?this.process(g):this)},process:function(a){var g=this;return a=e.grep(a,function(b){return g.matcher(b)}),a=this.sorter(a),a.length?this.render(a.slice(0,this.options.items)).show():this.shown?this.hide():this},matcher:function(b){return ~b.toLowerCase().indexOf(this.query.toLowerCase())},sorter:function(h){var g=[],k=[],j=[],i;while(i=h.shift()){i.toLowerCase().indexOf(this.query.toLowerCase())?~i.indexOf(this.query)?k.push(i):j.push(i):g.push(i)}return g.concat(k,j)},highlighter:function(g){var c=this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g,"\\$&");return g.replace(new RegExp("("+c+")","ig"),function(i,h){return"<strong>"+h+"</strong>"})},render:function(a){var g=this;return a=e(a).map(function(c,h){return c=e(g.options.item).attr("data-value",h),c.find("a").html(g.highlighter(h)),c[0]}),a.first().addClass("active"),this.$menu.html(a),this},next:function(a){var h=this.$menu.find(".active").removeClass("active"),g=h.next();g.length||(g=e(this.$menu.find("li")[0])),g.addClass("active")},prev:function(h){var g=this.$menu.find(".active").removeClass("active"),i=g.prev();i.length||(i=this.$menu.find("li").last()),i.addClass("active")},listen:function(){this.$element.on("focus",e.proxy(this.focus,this)).on("blur",e.proxy(this.blur,this)).on("keypress",e.proxy(this.keypress,this)).on("keyup",e.proxy(this.keyup,this)),this.eventSupported("keydown")&&this.$element.on("keydown",e.proxy(this.keydown,this)),this.$menu.on("click",e.proxy(this.click,this)).on("mouseenter","li",e.proxy(this.mouseenter,this)).on("mouseleave","li",e.proxy(this.mouseleave,this))},eventSupported:function(g){var c=g in this.$element;return c||(this.$element.setAttribute(g,"return;"),c=typeof this.$element[g]=="function"),c},move:function(b){if(!this.shown){return}switch(b.keyCode){case 9:case 13:case 27:b.preventDefault();break;case 38:b.preventDefault(),this.prev();break;case 40:b.preventDefault(),this.next()}b.stopPropagation()},keydown:function(a){this.suppressKeyPressRepeat=~e.inArray(a.keyCode,[40,38,9,13,27]),this.move(a)},keypress:function(b){if(this.suppressKeyPressRepeat){return}this.move(b)},keyup:function(b){switch(b.keyCode){case 40:case 38:case 16:case 17:case 18:break;case 9:case 13:if(!this.shown){return}this.select();break;case 27:if(!this.shown){return}this.hide();break;default:this.lookup()}b.stopPropagation(),b.preventDefault()},focus:function(b){this.focused=!0},blur:function(b){this.focused=!1,!this.mousedover&&this.shown&&this.hide()},click:function(b){b.stopPropagation(),b.preventDefault(),this.select(),this.$element.focus()},mouseenter:function(a){this.mousedover=!0,this.$menu.find(".active").removeClass("active"),e(a.currentTarget).addClass("active")},mouseleave:function(b){this.mousedover=!1,!this.focused&&this.shown&&this.hide()}};var f=e.fn.typeahead;e.fn.typeahead=function(a){return this.each(function(){var g=e(this),c=g.data("typeahead"),b=typeof a=="object"&&a;c||g.data("typeahead",c=new d(this,b)),typeof a=="string"&&c[a]()})},e.fn.typeahead.defaults={source:[],items:8,menu:'<ul class="typeahead dropdown-menu"></ul>',item:'<li><a href="#"></a></li>',minLength:1},e.fn.typeahead.Constructor=d,e.fn.typeahead.noConflict=function(){return e.fn.typeahead=f,this},e(document).on("focus.typeahead.data-api",'[data-provide="typeahead"]',function(a){var g=e(this);if(g.data("typeahead")){return}g.typeahead(g.data())})}(window.jQuery);