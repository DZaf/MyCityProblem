    var _kCord = new google.maps.LatLng(40.9900614, 22.8760793);
    var _pCord = new google.maps.LatLng(37.792336, 26.702184);
    
    console.log(google.maps.geometry.spherical.computeDistanceBetween(_kCord, _pCord)/1000)