$(document).ready(function() {

$("#radiusSearch").hide();
  
    
    $.get("../display.php",
    {
        displayType:"displayAll"
    },
    function(data, status){
        //$("my-list").append();
        var a=JSON.parse(data);
        $( "#my-list" ).empty();
        $.each( a.event, function( key, val ) {
            if(typeof val.votes.downVotes.email == "string")
            {
                 var downcount = 0;
            }
            else
            {
                var downcount = Object.keys(val.votes.downVotes.email).length-1;
            }
             if(typeof val.votes.upVotes.email == "string")
            {
                 var upcount = 1;
            }
            else
            {
               var upcount = Object.keys(val.votes.upVotes.email).length;
            }
            
            
            $("#my-list").append('<tr scope="row" class="clickable-tr"><th> <img src="'+val.fotoPath+'" alt="Smiley face" height="50" width="50"></th><td class="cat">'+val.category+'</td><td class="dat">'+val.sendTime.date.day+'/'+val.sendTime.date.month+'/'+val.sendTime.date.year+'</td><td><i class="glyphicon glyphicon-thumbs-up"> '+upcount+'</i><i class="glyphicon glyphicon-thumbs-down"> '+downcount+'</i></td><td><button type="button" class="btn btn-info btn-sm" data-info="'+val.description+'" data-lat="'+val.location.latitude+'" data-long="'+val.location.longtitude+'">Info</button></td></tr>');
            //  console.log(val);
        });
        
        var map = null;
  var myMarker;
  var myLatlng;

  function initializeGMap(lat, lng) {
    // console.log(""+lat + ' '+lng)
    myLatlng = new google.maps.LatLng(lat, lng);

    var myOptions = {
      zoom: 14.5,
      zoomControl: true,
      center: myLatlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    map = new google.maps.Map(document.getElementById("map"), myOptions);

    myMarker = new google.maps.Marker({
      position: myLatlng
    });
    myMarker.setMap(map);
  }
 
        
$(".clickable-tr").on('click', function() {
    var impath=$(this).find("img").attr('src');
    var catp=$(this).find(".cat").html();
    var datp=$(this).find(".dat").html();
    var dec=$(this).find(".btn").attr("data-info");
    var upvote=$(this).find(".glyphicon-thumbs-up").html();
    var downvore=$(this).find(".glyphicon-thumbs-down").html();
    var lat=$(this).find(".btn").attr("data-lat");
    var long=$(this).find(".btn").attr("data-long");
     initializeGMap(long, lat);
    $("#problem-image").attr('src',impath);
    $("#category-problem").html(catp);
    $("#date-problem").html(datp);
    $("#desc-problem").html(dec);
    $("#problem-like").html(upvote);
    $("#problem-dislike").html(downvore);
    $('#myModal').modal('show');
});
       
    });
    
     $.get("../display.php",
    {
        displayType:"displayWithId",
        id:"1"
    },
    function(data, status){
        //console.log("!!!!! ALLO DISPLAY !!!!!");
        //console.log(data);
    });
    
   $('#datepicker').datepicker({
            uiLibrary: 'bootstrap4',
            format: 'dd/mm/yyyy'
        });
        
          $('#datepicker1').datepicker({
            uiLibrary: 'bootstrap4',
            format: 'dd/mm/yyyy'
        });
        
        //console.log($('#datepicker').val());
    $('#datepicker').on("change", function() {
        
        var str=$('#datepicker').val();
             var res = str.split("/");
             var newnum= ''+res[2]+''+res[1]+''+res[0];
        
        if($('#datepicker1').val() !='')
         {
             var str=$('#datepicker').val();
             var res = str.split("/");
             var newnum= ''+res[2]+''+res[1]+''+res[0];
             
             var str1=$('#datepicker1').val();
             var res1 = str1.split("/");
             var newnum1= ''+res1[2]+''+res1[1]+''+res1[0];
             
            
             if(Number(newnum)>Number(newnum1))
             {
                 alert("Παρακαλώ εισάγεται ημερομηνία Μικρότερη απο αυτή στο 'Εως'!!");
                 $('#datepicker').val('');
             }
             else{
                 $.get("../display.php",
                    {
                         displayType:"displayBySpaceTime",
                         fromYear: res[2],
                         fromMonth: res[1],
                         fromDay: res[0],
                         toYear: res1[2],
                         toMonth: res1[1],
                         toDay: res1[0]
                         
                    },
                    function(data, status){
                        
        //$("my-list").append();
        var a=JSON.parse(data);
        $( "#my-list" ).empty();
        $.each( a, function( key, val ) {
            if(typeof val.votes.downVotes.email == "string")
            {
                 var downcount = 0;
            }
            else
            {
                var downcount = Object.keys(val.votes.downVotes.email).length-1;
            }
             if(typeof val.votes.upVotes.email == "string")
            {
                 var upcount = 1;
            }
            else
            {
               var upcount = Object.keys(val.votes.upVotes.email).length;
            }
            let lat = $("#radiusSearch").attr("data-lat");
            let lng = $("#radiusSearch").attr("data-long");
            let radius = document.getElementById("myRange").value;
            console.log("lat: "+lat + "long: "+ lng+" rad: " +radius);
            
            if((lat!='' || lat!='undefined') && lng!='' && (radius!=null || radius!='' || radius!=0) )
            {
                    var _kCord = new google.maps.LatLng(lng, lat);
                    var _pCord = new google.maps.LatLng(val.location.latitude, val.location.longtitude);
    
                 if((google.maps.geometry.spherical.computeDistanceBetween(_kCord, _pCord)/1000)<=radius)
                 {
                      $("#my-list").append('<tr scope="row" class="clickable-tr"><th> <img src="'+val.fotoPath+'" alt="Smiley face" height="50" width="50"></th><td class="cat">'+val.category+'</td><td class="dat">'+val.sendTime.date.day+'/'+val.sendTime.date.month+'/'+val.sendTime.date.year+'</td><td><i class="glyphicon glyphicon-thumbs-up"> '+upcount+'</i><i class="glyphicon glyphicon-thumbs-down"> '+downcount+'</i></td><td><button type="button" class="btn btn-info btn-sm" data-info="'+val.description+'" data-lat="'+val.location.latitude+'" data-long="'+val.location.longtitude+'">Info</button></td></tr>');
                 }
                 //console.log(google.maps.geometry.spherical.computeDistanceBetween(_kCord, _pCord)/1000);
               
            }
            else
            {
                 $("#my-list").append('<tr scope="row" class="clickable-tr"><th> <img src="'+val.fotoPath+'" alt="Smiley face" height="50" width="50"></th><td class="cat">'+val.category+'</td><td class="dat">'+val.sendTime.date.day+'/'+val.sendTime.date.month+'/'+val.sendTime.date.year+'</td><td><i class="glyphicon glyphicon-thumbs-up"> '+upcount+'</i><i class="glyphicon glyphicon-thumbs-down"> '+downcount+'</i></td><td><button type="button" class="btn btn-info btn-sm" data-info="'+val.description+'" data-lat="'+val.location.latitude+'" data-long="'+val.location.longtitude+'">Info</button></td></tr>');
            //  console.log(val);
            }

           
        });
        
        var map = null;
  var myMarker;
  var myLatlng;

  function initializeGMap(lat, lng) {
    // console.log(""+lat + ' '+lng)
    myLatlng = new google.maps.LatLng(lat, lng);

    var myOptions = {
      zoom: 14.5,
      zoomControl: true,
      center: myLatlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    map = new google.maps.Map(document.getElementById("map"), myOptions);

    myMarker = new google.maps.Marker({
      position: myLatlng
    });
    myMarker.setMap(map);
  }
 
        
$(".clickable-tr").on('click', function() {
    var impath=$(this).find("img").attr('src');
    var catp=$(this).find(".cat").html();
    var datp=$(this).find(".dat").html();
    var dec=$(this).find(".btn").attr("data-info");
    var upvote=$(this).find(".glyphicon-thumbs-up").html();
    var downvore=$(this).find(".glyphicon-thumbs-down").html();
    var lat=$(this).find(".btn").attr("data-lat");
    var long=$(this).find(".btn").attr("data-long");
     initializeGMap(long, lat);
    $("#problem-image").attr('src',impath);
    $("#category-problem").html(catp);
    $("#date-problem").html(datp);
    $("#desc-problem").html(dec);
    $("#problem-like").html(upvote);
    $("#problem-dislike").html(downvore);
    $('#myModal').modal('show');
});
       
    
        
                    });
             }
         }
         else{
             
             $.get("../display.php",
                    {
                         displayType:"displayBySpaceTime",
                         fromYear: res[2],
                         fromMonth: res[1],
                         fromDay: res[0]
                    },
                    function(data, status){
                        
        //$("my-list").append();
        var a=JSON.parse(data);
        $( "#my-list" ).empty();
        $.each( a, function( key, val ) {
            if(typeof val.votes.downVotes.email == "string")
            {
                 var downcount = 0;
            }
            else
            {
                var downcount = Object.keys(val.votes.downVotes.email).length-1;
            }
             if(typeof val.votes.upVotes.email == "string")
            {
                 var upcount = 1;
            }
            else
            {
               var upcount = Object.keys(val.votes.upVotes.email).length;
            }
            let lat = $("#radiusSearch").attr("data-lat");
            let lng = $("#radiusSearch").attr("data-long");
            let radius = document.getElementById("myRange").value;
            console.log("lat: "+lat + "long: "+ lng+" rad: " +radius);
             if((lat!='' || lat!='undefined') && lng!='' && (radius!=null || radius!='' || radius!=0) )
            {
                    var _kCord = new google.maps.LatLng(lng, lat);
                    var _pCord = new google.maps.LatLng(val.location.latitude, val.location.longtitude);
    
                 if((google.maps.geometry.spherical.computeDistanceBetween(_kCord, _pCord)/1000)<=radius)
                 {
                      $("#my-list").append('<tr scope="row" class="clickable-tr"><th> <img src="'+val.fotoPath+'" alt="Smiley face" height="50" width="50"></th><td class="cat">'+val.category+'</td><td class="dat">'+val.sendTime.date.day+'/'+val.sendTime.date.month+'/'+val.sendTime.date.year+'</td><td><i class="glyphicon glyphicon-thumbs-up"> '+upcount+'</i><i class="glyphicon glyphicon-thumbs-down"> '+downcount+'</i></td><td><button type="button" class="btn btn-info btn-sm" data-info="'+val.description+'" data-lat="'+val.location.latitude+'" data-long="'+val.location.longtitude+'">Info</button></td></tr>');
                 }
                 //console.log(google.maps.geometry.spherical.computeDistanceBetween(_kCord, _pCord)/1000);
               
            }
            else
            {
                $("#my-list").append('<tr scope="row" class="clickable-tr"><th> <img src="'+val.fotoPath+'" alt="Smiley face" height="50" width="50"></th><td class="cat">'+val.category+'</td><td class="dat">'+val.sendTime.date.day+'/'+val.sendTime.date.month+'/'+val.sendTime.date.year+'</td><td><i class="glyphicon glyphicon-thumbs-up"> '+upcount+'</i><i class="glyphicon glyphicon-thumbs-down"> '+downcount+'</i></td><td><button type="button" class="btn btn-info btn-sm" data-info="'+val.description+'" data-lat="'+val.location.latitude+'" data-long="'+val.location.longtitude+'">Info</button></td></tr>');
            //  console.log(val);
            }
            
        });
        
        var map = null;
  var myMarker;
  var myLatlng;

  function initializeGMap(lat, lng) {
    // console.log(""+lat + ' '+lng)
    myLatlng = new google.maps.LatLng(lat, lng);

    var myOptions = {
      zoom: 14.5,
      zoomControl: true,
      center: myLatlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    map = new google.maps.Map(document.getElementById("map"), myOptions);

    myMarker = new google.maps.Marker({
      position: myLatlng
    });
    myMarker.setMap(map);
  }
 
        
$(".clickable-tr").on('click', function() {
    var impath=$(this).find("img").attr('src');
    var catp=$(this).find(".cat").html();
    var datp=$(this).find(".dat").html();
    var dec=$(this).find(".btn").attr("data-info");
    var upvote=$(this).find(".glyphicon-thumbs-up").html();
    var downvore=$(this).find(".glyphicon-thumbs-down").html();
    var lat=$(this).find(".btn").attr("data-lat");
    var long=$(this).find(".btn").attr("data-long");
     initializeGMap(long, lat);
    $("#problem-image").attr('src',impath);
    $("#category-problem").html(catp);
    $("#date-problem").html(datp);
    $("#desc-problem").html(dec);
    $("#problem-like").html(upvote);
    $("#problem-dislike").html(downvore);
    $('#myModal').modal('show');
});
       
    
        
                    });
             
             
             
             
             
             
             
         }
    });
    
     $('#datepicker1').on("change", function() {
         
         var str1=$('#datepicker1').val();
             var res1 = str1.split("/");
             var newnum1= ''+res1[2]+''+res1[1]+''+res1[0];
         
         if($('#datepicker').val() !='')
         {
             var str=$('#datepicker').val();
             var res = str.split("/");
             var newnum= ''+res[2]+''+res[1]+''+res[0];
             
             
             
             if(Number(newnum)>Number(newnum1))
             {
                 alert("Παρακαλώ εισάγεται ημερομηνία μεγαλύτερη απο αυτή στο 'Από'!!");
                 $('#datepicker1').val('');
             }
             else{
                 $.get("../display.php",
                    {
                         displayType:"displayBySpaceTime",
                         fromYear: res[2],
                         fromMonth: res[1],
                         fromDay: res[0],
                         toYear: res1[2],
                         toMonth: res1[1],
                         toDay: res1[0]
                         
                    },
                    function(data, status){
                        
        //$("my-list").append();
        var a=JSON.parse(data);
        $( "#my-list" ).empty();
        $.each( a, function( key, val ) {
            if(typeof val.votes.downVotes.email == "string")
            {
                 var downcount = 0;
            }
            else
            {
                var downcount = Object.keys(val.votes.downVotes.email).length-1;
            }
             if(typeof val.votes.upVotes.email == "string")
            {
                 var upcount = 1;
            }
            else
            {
               var upcount = Object.keys(val.votes.upVotes.email).length;
            }
            
            let lat = $("#radiusSearch").attr("data-lat");
            let lng = $("#radiusSearch").attr("data-long");
            let radius = document.getElementById("myRange").value;
            console.log("lat: "+lat + "long: "+ lng+" rad: " +radius);
             if((lat!='' || lat!='undefined') && lng!='' && (radius!=null || radius!='' || radius!=0) )
            {
                    var _kCord = new google.maps.LatLng(lng, lat);
                    var _pCord = new google.maps.LatLng(val.location.latitude, val.location.longtitude);
    
                 if((google.maps.geometry.spherical.computeDistanceBetween(_kCord, _pCord)/1000)<=radius)
                 {
                      $("#my-list").append('<tr scope="row" class="clickable-tr"><th> <img src="'+val.fotoPath+'" alt="Smiley face" height="50" width="50"></th><td class="cat">'+val.category+'</td><td class="dat">'+val.sendTime.date.day+'/'+val.sendTime.date.month+'/'+val.sendTime.date.year+'</td><td><i class="glyphicon glyphicon-thumbs-up"> '+upcount+'</i><i class="glyphicon glyphicon-thumbs-down"> '+downcount+'</i></td><td><button type="button" class="btn btn-info btn-sm" data-info="'+val.description+'" data-lat="'+val.location.latitude+'" data-long="'+val.location.longtitude+'">Info</button></td></tr>');
                 }
                 //console.log(google.maps.geometry.spherical.computeDistanceBetween(_kCord, _pCord)/1000);
               
            }
            else
            {
                 $("#my-list").append('<tr scope="row" class="clickable-tr"><th> <img src="'+val.fotoPath+'" alt="Smiley face" height="50" width="50"></th><td class="cat">'+val.category+'</td><td class="dat">'+val.sendTime.date.day+'/'+val.sendTime.date.month+'/'+val.sendTime.date.year+'</td><td><i class="glyphicon glyphicon-thumbs-up"> '+upcount+'</i><i class="glyphicon glyphicon-thumbs-down"> '+downcount+'</i></td><td><button type="button" class="btn btn-info btn-sm" data-info="'+val.description+'" data-lat="'+val.location.latitude+'" data-long="'+val.location.longtitude+'">Info</button></td></tr>');
            //  console.log(val);
            }
           
        });
        
        var map = null;
  var myMarker;
  var myLatlng;

  function initializeGMap(lat, lng) {
    // console.log(""+lat + ' '+lng)
    myLatlng = new google.maps.LatLng(lat, lng);

    var myOptions = {
      zoom: 14.5,
      zoomControl: true,
      center: myLatlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    map = new google.maps.Map(document.getElementById("map"), myOptions);

    myMarker = new google.maps.Marker({
      position: myLatlng
    });
    myMarker.setMap(map);
  }
 
        
$(".clickable-tr").on('click', function() {
    var impath=$(this).find("img").attr('src');
    var catp=$(this).find(".cat").html();
    var datp=$(this).find(".dat").html();
    var dec=$(this).find(".btn").attr("data-info");
    var upvote=$(this).find(".glyphicon-thumbs-up").html();
    var downvore=$(this).find(".glyphicon-thumbs-down").html();
    var lat=$(this).find(".btn").attr("data-lat");
    var long=$(this).find(".btn").attr("data-long");
     initializeGMap(long, lat);
    $("#problem-image").attr('src',impath);
    $("#category-problem").html(catp);
    $("#date-problem").html(datp);
    $("#desc-problem").html(dec);
    $("#problem-like").html(upvote);
    $("#problem-dislike").html(downvore);
    $('#myModal').modal('show');
});
       
    
        
                    });
             }
             
             
             
             //console.log(newnum);
         }
         else
         {
             $.get("../display.php",
                    {
                         displayType:"displayBySpaceTime",
                         toYear: res1[2],
                         toMonth: res1[1],
                         toDay: res1[0]
                         
                    },
                    function(data, status){
                        
        //$("my-list").append();
        var a=JSON.parse(data);
        $( "#my-list" ).empty();
        $.each( a, function( key, val ) {
            if(typeof val.votes.downVotes.email == "string")
            {
                 var downcount = 0;
            }
            else
            {
                var downcount = Object.keys(val.votes.downVotes.email).length-1;
            }
             if(typeof val.votes.upVotes.email == "string")
            {
                 var upcount = 1;
            }
            else
            {
               var upcount = Object.keys(val.votes.upVotes.email).length;
            }
            
            let lat = $("#radiusSearch").attr("data-lat");
            let lng = $("#radiusSearch").attr("data-long");
            let radius = document.getElementById("myRange").value;
            console.log("lat: "+lat + "long: "+ lng+" rad: " +radius);
            if((lat!='' || lat!='undefined') && lng!='' && (radius!=null || radius!='' || radius!=0) )
            {
                    var _kCord = new google.maps.LatLng(lng, lat);
                    var _pCord = new google.maps.LatLng(val.location.latitude, val.location.longtitude);
    
                 if((google.maps.geometry.spherical.computeDistanceBetween(_kCord, _pCord)/1000)<=radius)
                 {
                      $("#my-list").append('<tr scope="row" class="clickable-tr"><th> <img src="'+val.fotoPath+'" alt="Smiley face" height="50" width="50"></th><td class="cat">'+val.category+'</td><td class="dat">'+val.sendTime.date.day+'/'+val.sendTime.date.month+'/'+val.sendTime.date.year+'</td><td><i class="glyphicon glyphicon-thumbs-up"> '+upcount+'</i><i class="glyphicon glyphicon-thumbs-down"> '+downcount+'</i></td><td><button type="button" class="btn btn-info btn-sm" data-info="'+val.description+'" data-lat="'+val.location.latitude+'" data-long="'+val.location.longtitude+'">Info</button></td></tr>');
                 }
                // console.log(google.maps.geometry.spherical.computeDistanceBetween(_kCord, _pCord)/1000);
               
            }
            else
            {
               $("#my-list").append('<tr scope="row" class="clickable-tr"><th> <img src="'+val.fotoPath+'" alt="Smiley face" height="50" width="50"></th><td class="cat">'+val.category+'</td><td class="dat">'+val.sendTime.date.day+'/'+val.sendTime.date.month+'/'+val.sendTime.date.year+'</td><td><i class="glyphicon glyphicon-thumbs-up"> '+upcount+'</i><i class="glyphicon glyphicon-thumbs-down"> '+downcount+'</i></td><td><button type="button" class="btn btn-info btn-sm" data-info="'+val.description+'" data-lat="'+val.location.latitude+'" data-long="'+val.location.longtitude+'">Info</button></td></tr>'); 
               
              // console.log("NOT yeah");
            }
            
            
            //  console.log(val);
        });
        
        var map = null;
  var myMarker;
  var myLatlng;

  function initializeGMap(lat, lng) {
    // console.log(""+lat + ' '+lng)
    myLatlng = new google.maps.LatLng(lat, lng);

    var myOptions = {
      zoom: 14.5,
      zoomControl: true,
      center: myLatlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    map = new google.maps.Map(document.getElementById("map"), myOptions);

    myMarker = new google.maps.Marker({
      position: myLatlng
    });
    myMarker.setMap(map);
  }
 
        
$(".clickable-tr").on('click', function() {
    var impath=$(this).find("img").attr('src');
    var catp=$(this).find(".cat").html();
    var datp=$(this).find(".dat").html();
    var dec=$(this).find(".btn").attr("data-info");
    var upvote=$(this).find(".glyphicon-thumbs-up").html();
    var downvore=$(this).find(".glyphicon-thumbs-down").html();
    var lat=$(this).find(".btn").attr("data-lat");
    var long=$(this).find(".btn").attr("data-long");
     initializeGMap(long, lat);
    $("#problem-image").attr('src',impath);
    $("#category-problem").html(catp);
    $("#date-problem").html(datp);
    $("#desc-problem").html(dec);
    $("#problem-like").html(upvote);
    $("#problem-dislike").html(downvore);
    $('#myModal').modal('show');
});
       
    
        
                    });
         }
    });
       
       
//EDW EKSAFANIZEIS TO KOUMPAKI ANAZITISIS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        
        $("#mySlider").hide();
        $("#findLocMap").hide();
        
        $("#showSlider").on('click', function() {
            //EDW EMFANIZEIS TO KOUMPAKI ANAZITISIS
            $(".mySlider2").hide();
            $("#mySlider").show("fast");
            $("#findLocMap").show("fast")
            loadMap();

            
        });
        
         $("#hideSlider").on('click', function() {
             //EDW EKSAFANIZEIS TO KOUMPAKI ANAZITISIS
            $(".mySlider2").show();
            $("#mySlider").hide("fast");
            $("#findLocMap").hide("fast");
        });
        
        
     var slider = document.getElementById("myRange");
var output = document.getElementById("demo");
output.innerHTML = slider.value;

slider.oninput = function() {
  output.innerHTML = this.value;
}


    //George Here  !!!!!!!!!!!!!! 
       

//   var myLatLng1 = {lat: -25.363, lng: 131.044};

//   var map2 = new google.maps.Map(document.getElementById('findLocMap'), {
//     zoom: 4,
//     center: myLatLng1
//   });

//   var marker = new google.maps.Marker({
//     position: myLatLng1,
//     map: map2,
//     title: 'Hello World!'
//   });
//function loadMap(){
var map, infoWindow;

  map = new google.maps.Map(document.getElementById('findLocMap'), {
    center: {lat: -0.0, lng: 0.0},
    zoom: 1
  });
  function loadMap(){
  infoWindow = new google.maps.InfoWindow;

  // Try HTML5 geolocation.
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      var pos = {
        lat: position.coords.latitude,
        lng: position.coords.longitude
      };

      $("#radiusSearch").attr("data-lat",pos.lat);
      $("#radiusSearch").attr("data-long",pos.lng);

      infoWindow.setPosition(pos);
      map.zoom = 15;
      infoWindow.setContent('Location found.');
      infoWindow.open(map);
      map.setCenter(pos);
    }, function() {
      handleLocationError(true, infoWindow, map.getCenter());
    });
  } else {
    // Browser doesn't support Geolocation
    
    handleLocationError(false, infoWindow, map.getCenter());
  }


function handleLocationError(browserHasGeolocation, infoWindow, pos) {
  infoWindow.setPosition(pos);
  infoWindow.setContent(browserHasGeolocation ?
                        'Error: The Geolocation service failed.' :
                        'Error: Your browser doesn\'t support geolocation.');
                        console.log("browserHasGeolocation" + browserHasGeolocation);
                            $("#radiusSearch").prop('disabled', true);
  infoWindow.open(map);
  
                    }}
//}

// $("#radiusSearch").click(function(){

// let lat = $("#radiusSearch").attr("data-lat");
// let lng = $("#radiusSearch").attr("data-long");
// let radius = document.getElementById("myRange").value;

//$('#datepicker').datepicker("show");
//$(".ui-datepicker-current-day").trigger("click")
//console.log($('#datepicker').click());
// if($('tr').length>1)
// {
//     $.each($('tr'), function( key, val ) {
        
//         var lat1=$(this).find("button").attr("data-lat");
//         var long2=$(this).find("button").attr("data-long");
//         var _kCord = new google.maps.LatLng(lng, lat);
//         var _pCord = new google.maps.LatLng(lat1, long2);
//         if((google.maps.geometry.spherical.computeDistanceBetween(_kCord, _pCord)/1000)>radius)
//         {
//             $(this).hide();
//         }
//     });
// }



// });



       //George END Here  !!!!!!!!!!!!!! 



    
});

