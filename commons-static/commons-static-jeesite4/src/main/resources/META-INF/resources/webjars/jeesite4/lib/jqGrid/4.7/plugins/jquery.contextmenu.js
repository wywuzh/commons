(function(e){var a,i,g,d,b;var c={menuStyle:{listStyle:"none",padding:"1px",margin:"0px",backgroundColor:"#fff",border:"1px solid #999",width:"100px"},itemStyle:{margin:"0px",color:"#000",display:"block",cursor:"default",padding:"3px",border:"1px solid #fff",backgroundColor:"transparent"},itemHoverStyle:{border:"1px solid #0a246a",backgroundColor:"#b6bdd2"},eventPosX:"pageX",eventPosY:"pageY",shadow:true,onContextMenu:null,onShowMenu:null};e.fn.contextMenu=function(l,k){if(!a){a=e('<div id="jqContextMenu"></div>').hide().css({position:"absolute",zIndex:"500"}).appendTo("body").bind("click",function(m){m.stopPropagation()})}if(!i){i=e("<div></div>").css({backgroundColor:"#000",position:"absolute",opacity:0.2,zIndex:499}).appendTo("body").hide()}d=d||[];d.push({id:l,menuStyle:e.extend({},c.menuStyle,k.menuStyle||{}),itemStyle:e.extend({},c.itemStyle,k.itemStyle||{}),itemHoverStyle:e.extend({},c.itemHoverStyle,k.itemHoverStyle||{}),bindings:k.bindings||{},shadow:k.shadow||k.shadow===false?k.shadow:c.shadow,onContextMenu:k.onContextMenu||c.onContextMenu,onShowMenu:k.onShowMenu||c.onShowMenu,eventPosX:k.eventPosX||c.eventPosX,eventPosY:k.eventPosY||c.eventPosY});var j=d.length-1;e(this).bind("contextmenu",function(n){var m=(!!d[j].onContextMenu)?d[j].onContextMenu(n):true;b=n.target;if(m){h(j,this,n);return false}});return this};function h(k,j,l){var m=d[k];g=e("#"+m.id).find("ul:first").clone(true);g.css(m.menuStyle).find("li").css(m.itemStyle).hover(function(){e(this).css(m.itemHoverStyle)},function(){e(this).css(m.itemStyle)}).find("img").css({verticalAlign:"middle",paddingRight:"2px"});a.html(g);if(!!m.onShowMenu){a=m.onShowMenu(l,a)}e.each(m.bindings,function(o,n){e("#"+o,a).bind("click",function(){f();n(j,b)})});a.css({left:l[m.eventPosX],top:l[m.eventPosY]}).show();if(m.shadow){i.css({width:a.width(),height:a.height(),left:l.pageX+2,top:l.pageY+2}).show()}e(document).one("click",f)}function f(){a.hide();i.hide()}e.contextMenu={defaults:function(j){e.each(j,function(k,l){if(typeof l=="object"&&c[k]){e.extend(c[k],l)}else{c[k]=l}})}}})(jQuery);$(function(){$("div.contextMenu").hide()});