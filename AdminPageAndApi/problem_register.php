<?php

if (isset($_POST['category'])){
    $category = $_POST['category'];

    if (isset($_POST['hour'])){
        $hour = $_POST['hour'];

        if (isset($_POST['day'])){
            $day = $_POST['day'];

            if (isset($_POST['month'])){
                $month = $_POST['month'];
                
                if (isset($_POST['year'])){
                    $year = $_POST['year'];

                    if (isset($_POST['longtitude'])){
                        $longtitude = $_POST['longtitude'];

                        if (isset($_POST['latitude'])){
                            $latitude = $_POST['latitude'];

                            if (isset($_POST['fotopath'])){
                                $fotopath = $_POST['fotopath'];

                                if (isset($_POST['description'])){
                                    $description = $_POST['description'];
                                    if (isset($_POST['mail'])){
                                    $mail = $_POST['mail'];

                                    if (file_exists( './databse.xml')) {

                                        $xml = simplexml_load_file("databse.xml");

                                        $newEvent = $xml->events->addChild('event');
                                        $counter= count($xml->events->event);
                                        $newEvent->addAttribute('id',  $counter);
                                        $newEvent->addChild('category',$category);

                                        $newEvent2 = $newEvent->addChild('sendTime');
                                        $newEvent2->addChild('hour',$hour);

                                        $newEvent3 = $newEvent2->addChild('date');;
                                        $newEvent3->addChild('day',$day);
                                        $newEvent3->addChild('month',$month);
                                        $newEvent3->addChild('year',$year);

                                        $newEvent4 = $newEvent->addChild('location');
                                        $newEvent4->addChild('longtitude',$longtitude);
                                        $newEvent4->addChild('latitude',$latitude);

                                        $newEvent ->addChild('fotoPath',$fotopath);
                                        $newEvent ->addChild('description',$description);
                                        
                                        $newEvent5 = $newEvent ->addChild('votes');
                                        $newEvent6 = $newEvent5 -> addChild('upVotes');
                                        $newEvent6 ->addChild('email',$mail);
                                        $newEvent7 = $newEvent5 -> addChild('downVotes');
                                        $newEvent7 ->addChild('email',$mail);

                                        file_put_contents("databse.xml",$xml->asXML());

                                       
                                        echo json_encode(array("success" => true, "message" => "Register Problem completed succesful","Photo_Name"=>$counter), JSON_FORCE_OBJECT);
                                        http_response_code(200);

                                    }else{
                                        http_response_code(400);
                                        echo json_encode(array("success" => false, "message" => "XML file does not exist"), JSON_FORCE_OBJECT);  
                                    }
                                    }else
                                    {
                                         http_response_code(400);
                                        echo json_encode(array("success" => false, "message" => "mail does not exist"), JSON_FORCE_OBJECT);  
                                    }
                                }else{
                                    http_response_code(400);
                                    echo json_encode(array("succes" => false, "message" => "Description does not exist"), JSON_FORCE_OBJECT);
                                }
                            }else{
                                http_response_code(400);
                                echo json_encode(array("succes" => false, "message" => "Fotopath does not exist"), JSON_FORCE_OBJECT);
                            }
                        }else{
                            http_response_code(400);
                            echo json_encode(array("succes" => false, "message" => "Latitude does not exist"), JSON_FORCE_OBJECT);
                        }
                    }else{
                        http_response_code(400);
                        echo json_encode(array("succes" => false, "message" => "Longitude does not exist"), JSON_FORCE_OBJECT);
                    }      
                }else{
                    http_response_code(400);
                    echo json_encode(array("succes" => false, "message" => "Year does not exist"), JSON_FORCE_OBJECT);
                }
            }else{
                http_response_code(400);
                echo json_encode(array("succes" => false, "message" => "Month does not exist"), JSON_FORCE_OBJECT);
            }
        }else{
            http_response_code(400);
            echo json_encode(array("succes" => false, "message" => "Day does not exist"), JSON_FORCE_OBJECT);
        }
    }else{
        http_response_code(400);
        echo json_encode(array("succes" => false, "message" => "Hour does not exist"), JSON_FORCE_OBJECT);
    }
}else{
    http_response_code(400);
    echo json_encode(array("succes" => false, "message" => "Category does not exist"), JSON_FORCE_OBJECT);
}
