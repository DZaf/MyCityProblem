<?php 

if(isset($_GET['displayType'])){
$displayType = $_GET['displayType'];

    if($displayType == 'displayAll'){
        
        displayAll();

    }else if($displayType == 'displayWithId'){

        if(isset($_GET['id'])){

            $id = $_GET['id'];

            displayWithId($id);

        }else{
            http_response_code(400);
            echo json_encode(array("success" => false, "message" => "Id does not exist"), JSON_FORCE_OBJECT);
        }

    }else if($displayType == 'displayBySpaceTime'){
        $from = false;
        $to = false;
        $distance = false;

         

        if(isset($_GET['fromYear']) && isset($_GET['fromMonth']) && isset($_GET['fromDay'])){

            $from = true;
            $fromDate = $_GET['fromYear'] . $_GET['fromMonth'] . $_GET['fromDay'] ;
        }
            if(isset($_GET['toYear']) && isset($_GET['toMonth']) && isset($_GET['toDay'])){

                $to = true;
                $toDate = $_GET['toYear'] . $_GET['toMonth'] . $_GET['toDay'];
            }
                if(isset($_GET['currentLat']) && isset($_GET['currentLong']) && isset($_GET['radius'])){

                    $distance = true;
                    $currentLat = $_GET['currentLat'] ; $currentLong = $_GET['currentLong'];
                    $radius = $_GET['radius'];

                }

                if (file_exists( './databse.xml')) {
                    $xml = simplexml_load_file("databse.xml");
                    if($from == true && $to == false && $distance == false){

                    $result = $xml->xpath("/root/events/event[number(concat(sendTime/date/year/text(),sendTime/date/month/text(),sendTime/date/day/text()))>=".$fromDate."]");
                    http_response_code(200);
                    echo $json = json_encode($result, JSON_FORCE_OBJECT);

                    }else if($to == true && $from == false && $distance == false){
                        
                        $result = $xml->xpath("/root/events/event[number(concat(sendTime/date/year/text(),sendTime/date/month/text(),sendTime/date/day/text()))<=".$toDate."]");
                    http_response_code(200);
                    echo $json = json_encode($result, JSON_FORCE_OBJECT);
    
                    }else if($distance == true && $to == false && $from == false){
                        
                 //$result = $xml->xpath("/root/events/event[math:sqrt(math:pow(number(number(location/longtitude/text())-".$currentLong."),2)+math:pow(number(number(location/latitude/text())-".$currentLat."),2))<".$radius."]");
                 //$result = $xml->xpath("/root/events/event[math:sqrt(math:pow(number(number(location/longtitude/text())-35.792336),2)+math:pow(number(number(location/latitude/text())-24.702184),2))<3]");

                 $xml->registerXPathNamespace("math", "http://www.w3.org/2005/xpath-functions/math/");
                 $result = $xml->xpath("math:sqrt(20)");
                 http_response_code(200);
                    echo $json = json_encode($result, JSON_FORCE_OBJECT);
    
                    }else if($from == true && $to == true && $distance == false){

                        $result = $xml->xpath("/root/events/event[number(concat(sendTime/date/year/text(),sendTime/date/month/text(),sendTime/date/day/text()))>=".$fromDate." and number(concat(sendTime/date/year/text(),sendTime/date/month/text(),sendTime/date/day/text()))<=".$toDate."]");
                    http_response_code(200);
                    echo $json = json_encode($result, JSON_FORCE_OBJECT);
    
                    }else if($from == true && $distance == true && $to == false){

                        $result = $xml->xpath("/root/events/event[number(concat(sendTime/date/year/text(),sendTime/date/month/text(),sendTime/date/day/text()))>=".$fromDate." and math:sqrt(math:pow(number(number(location/longtitude/text())-".$currentLong."),2)+math:pow(number(number(location/latitude/text())-".$currentLat."),2))<".$radius."]");
                    http_response_code(200);
                    echo $json = json_encode($result, JSON_FORCE_OBJECT);
                        
                    }else if($to == true && $distance == true && $from == false){

                        $result = $xml->xpath("/root/events/event[number(concat(sendTime/date/year/text(),sendTime/date/month/text(),sendTime/date/day/text()))<=".$toDate." and math:sqrt(math:pow(number(number(location/longtitude/text())-".$currentLong."),2)+math:pow(number(number(location/latitude/text())-".$currentLat."),2))<".$radius."]");
                    http_response_code(200);
                    echo $json = json_encode($result, JSON_FORCE_OBJECT);
    
                    }else if($from == true && $to ==true && $distance == true){

                        $result = $xml->xpath("/root/events/event[number(concat(sendTime/date/year/text(),sendTime/date/month/text(),sendTime/date/day/text()))>=".$fromDate." and number(concat(sendTime/date/year/text(),sendTime/date/month/text(),sendTime/date/day/text()))<=".$toDate." and math:sqrt(math:pow(number(number(location/longtitude/text())-".$currentLong."),2)+math:pow(number(number(location/latitude/text())-".$currentLat."),2))<".$radius."]");
                    http_response_code(200);
                    echo $json = json_encode($result, JSON_FORCE_OBJECT);
    
                    }else{
                        
                        $result = $xml->xpath("/root/events/event");
                    http_response_code(200);
                    echo $json = json_encode($result, JSON_FORCE_OBJECT);

                    }
              
            }else{
                http_response_code(400);
                echo json_encode(array("success" => false, "message" => "XML file does not exist"), JSON_FORCE_OBJECT);
            }

        }
    
}else{
    http_response_code(400);
    echo json_encode(array("success" => false, "message" => "Display type does not exist"), JSON_FORCE_OBJECT);
}


function displayAll(){
    
    if (file_exists( './databse.xml')) {

        $xml = simplexml_load_file("databse.xml");
        $entries = $xml->events;
        
        http_response_code(200);
        echo $json = json_encode($entries, JSON_FORCE_OBJECT);
        

    }else{
        http_response_code(400);
        echo json_encode(array("success" => false, "message" => "XML file does not exist"), JSON_FORCE_OBJECT);
    }

}

function displayWithId($id){

    if (file_exists( './databse.xml')) {

        $xml = simplexml_load_file("databse.xml");
        $entries = $xml->events->event;

        foreach ($entries as $entry) {

            if ($entry['id']==$id){

                http_response_code(200);
                echo $json = json_encode($entry, JSON_FORCE_OBJECT);
                break;

            }
        }
        
    }else{
        http_response_code(400);
        echo json_encode(array("success" => false, "message" => "XML file does not exist"), JSON_FORCE_OBJECT);
    }

}



