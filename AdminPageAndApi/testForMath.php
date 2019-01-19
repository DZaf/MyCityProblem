<?php
$xml = simplexml_load_file("databse.xml");
$xml->registerXPathNamespace("fn", "http://www.w3.org/2005/xpath-functions");
$result = $xml->xpath("/root/events/event[fn:abs((number(location/longtitude/text())-35.792336))+fn:abs((number(location/latitude/text())-24.702184))<=4]");

              
                 http_response_code(200);
                    echo $json = json_encode($result, JSON_FORCE_OBJECT);
?>