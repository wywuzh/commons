/*!
 * strength.js
 * Original author: @aaronlumsden
 * Further changes, comments: @aaronlumsden
 * Licensed under the MIT license
 */
(function(e,c,a,f){var d="strength";function b(h,g){this.element=h;this.$elem=e(this.element);this.options=e.extend({},e.fn.strength.defaults,g);this._defaults=e.fn.strength.defaults;this._name=d;this.init()}b.prototype={init:function(){var q=this.options;var m=new RegExp("[A-Z]");var o=new RegExp("[a-z]");var l=new RegExp("[0-9]");var k=new RegExp("[~!@#$%\\^&\\*()_+\\{\\}:\"\\|<>?`\\-=\\[\\];\\'\\\\,\\./]");function h(v,y){var u=v.length>=8?1:0;var r=v.match(m)?1:0;var t=v.match(o)?1:0;var x=v.match(l)?1:0;var s=v.match(k)?1:0;var w=u+r+t+x+s;i(w,y);q.strengthCheck(v,w)}function i(s,t){var r=e('div[data-meter="'+t+'"]').removeClass();r.parent().removeClass().addClass(q.strengthMeterClass);if(s==0){r.html("")}else{if(s==1){r.parent().addClass("veryweak");r.addClass("veryweak").html("<p>"+q.veryweakText+"</p>")}else{if(s==2){r.parent().addClass("weak");r.addClass("weak").html("<p>"+q.weakText+"</p>")}else{if(s==3||s==4){r.parent().addClass("medium");r.addClass("medium").html("<p>"+q.mediumText+"</p>")}else{r.parent().addClass("strong");r.addClass("strong").html("<p>"+q.strongText+"</p>")}}}}}var n=false;var p=q.strengthTipText+" "+q.strengthButtonText;var g=q.strengthTipText+" "+q.strengthButtonTextToggle;var j=this.$elem.attr("id");this.$elem.parent().addClass(q.strengthClass);this.$elem.addClass(q.strengthInputClass).attr("data-password",j).after('<input style="display:none" class="'+this.$elem.attr("class")+'" data-password="'+j+'" type="text" name="" value=""><a data-password-button="'+j+'" href="javascript:" class="'+q.strengthButtonClass+'" tabindex="-1">'+p+'</a><div class="'+q.strengthMeterClass+'"><div data-meter="'+j+'"><p></p></div></div>');this.$elem.bind("keyup keydown",function(s){thisval=e("#"+j).val();var r=e('input[type="text"][data-password="'+j+'"]').val(thisval);try{r.resetValid()}catch(t){}h(thisval,j)});e('input[type="text"][data-password="'+j+'"]').bind("keyup keydown",function(r){thisval=e('input[type="text"][data-password="'+j+'"]').val();e('input[type="password"][data-password="'+j+'"]').val(thisval);h(thisval,j)});e(a.body).on("click","."+q.strengthButtonClass,function(r){r.preventDefault();thisclass="hide_"+e(this).attr("class");if(n){e('input[type="text"][data-password="'+j+'"]').hide();e('input[type="password"][data-password="'+j+'"]').show().focus();e('a[data-password-button="'+j+'"]').removeClass(thisclass).html(p);n=false}else{e('input[type="text"][data-password="'+j+'"]').show().focus();e('input[type="password"][data-password="'+j+'"]').hide();e('a[data-password-button="'+j+'"]').addClass(thisclass).html(g);n=true}})}};e.fn[d]=function(g){return this.each(function(){if(!e.data(this,"plugin_"+d)){e.data(this,"plugin_"+d,new b(this,g))}})};e.fn[d].defaults={strengthClass:"strength",strengthInputClass:"strength_input",strengthMeterClass:"strength_meter",strengthButtonClass:"button_strength",strengthTipText:"密码填写建议：长度不小于8位，且包含，大写英文字母、小写英文字母、数字和符号。",strengthButtonText:"点击显示密码。",strengthButtonTextToggle:"点击隐藏密码。",veryweakText:"密码太弱啦！",weakText:"密码比较弱哦！",mediumText:"密码较安全！",strongText:"密码很强很安全！",strengthCheck:function(g,h){}}})(jQuery,window,document);