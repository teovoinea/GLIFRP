var map;
window.distance = 0;
window.crime = 0;
window.price = 0;
window.menuOpen = false;
function setData(selector,percent){
  var transform_styles = ['-webkit-transform',
                        '-ms-transform',
                        'transform'];
 var rotation = 180 * percent;
 var fill_rotation = rotation;
 var fix_rotation = rotation*2;
 for(var i in transform_styles) {
  $(selector + " .fill, "+selector+" .mask.full").css(transform_styles[i],"rotate("+fill_rotation+"deg)");
  $(selector+" .fill.fix").css(transform_styles[i],"rotate("+fix_rotation+"deg)");
 }
 $(selector + " .stat-title span").html(parseInt(percent * 100)+"%");
}

function explore(){
  $("#components *").animate({"opacity":0},100).css({"pointer-events":"none"});
  $("#show-menu, #show-menu *").delay(100).animate({"opacity":1},100).css({"pointer-events":"auto"});
}

function menu(){
  $("#show-menu, #show-menu *").animate({"opacity":0},100).css({"pointer-events":"none"});
  $("#components *").delay(100).animate({"opacity":1},100).css({"pointer-events":"auto"});
}

function moreinfo(){
  $("#info-popup").animate({"opacity":1},100).css({"display":"initial","pointer-events":"auto"});
}

function closeinfo(){
  $("#info-popup").animate({"opacity":0},100).css({"display":"none","pointer-events":"none"});
}

$(document).ready(function(){
  map = L.map('map').setView([40.7127, -74.0059], 13);

  L.tileLayer('https://{s}.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
    maxZoom: 18,
    minZoom:1,
    id: 'futureprog.pamdkofg',
    accessToken: 'pk.eyJ1IjoiZnV0dXJlcHJvZyIsImEiOiJjaWxkeHh2cjIwZWVzdnJtMHBnd2FkZmR3In0.IZfUd7-WTktf4Jz6ViDM_A'
  }).addTo(map);

  /*var popup = L.popup();
  var circle= L.circle([0,0],0,{
    color: "red",
    fillColor: "#FF0600",
    fillOpacity:0.3
  });
  function onMapClick(e){
    popup.setLatLng(e.latlng).setContent("You clicked the map at " + e.latlng.toString());
    popup.openOn(map);
    circle.setLatLng(e.latlng);
    circle.setRadius(400);
    circle.addTo(map);
  }
  map.on('click',onMapClick);*/

  function onInputFocus(){
      if(window.menuOpen)return;
    var left = $("#top-bar h1").outerWidth() + 3;
    var top = $("#search-box").innerHeight();
    $("#menu").css({"left":"calc("+left+"px + 4vw)","top":top+"px","tab-index":2}).slideDown(20);
    window.menuOpen = true;
  }
  window.onInputFocus = onInputFocus;
  var mouseInMenu = false;
  function onInputBlur(){
    if(!window.menuOpen)return;
    if(!mouseInMenu)$("#menu").slideUp(40).css({'tab-index':-1});
    window.menuOpen = false;
  }
  window.onInputBlur = onInputBlur;
  $("#menu").slideUp(0);
  $("#search-box").on('focus',onInputFocus).on('blur',onInputBlur).on('click',onInputFocus);
  $("#menu").mouseenter(function(){mouseInMenu = true;});
  $("#menu").mouseleave(function(){
    mouseInMenu = false;
    if(!$("#search-box").is(":focus") && !$("#menu .ui-slider .ui-slider-handle").is(":focus")){
      onInputBlur();
    }
  });

  $("#slider-crime").slider({
    value: 10,
    min: 0,
    max: 100,
    step: 2,
    slide: function(event,ui){
      $("#amount-crime").html(ui.value+"");
      window.crime = ui.value;
    }
  });
  $("#amount-crime").html(10+"");
  window.crime=10;

  $("#slider-price").slider({
    value: 150000,
    min: 0,
    max: 1000000,
    step: 200,
    slide: function(event,ui){
      $("#amount-price").html("$" + ui.value+"");
      window.price = ui.value;
    }
  });
  $("#amount-price").html("$5000");
  window.price = 5000;
    $("#slider-distance").slider({
        value: 2,
        min: 0,
        max: 50,
        step: 1,
        slide: function(event,ui){
            $("#amount-distance").html(ui.value +" states away");
            window.distance = ui.value;
        }
    });
    $("#amount-distance").html("2 states away");
    window.distance = 2;

  $("#info-popup").animate({"opacity":0},0).css({"display":"none","pointer-events":"none"});
  setData("#stat-bar",0.0);
});

/**L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
}).addTo(map);

L.marker([51.5, -0.09]).addTo(map)
    .bindPopup('A pretty CSS3 popup.<br> Easily customizable.')
    .openPopup();
**/
