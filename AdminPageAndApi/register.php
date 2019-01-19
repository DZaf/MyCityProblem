<?php

if (isset($_POST['email'])){
    $email = $_POST['email'];
    if (filter_var($email, FILTER_VALIDATE_EMAIL)) {

        if(isset($_POST['name'])){

            $name = $_POST['name'];

            if (isset($_POST['surname'])){

                $surname = $_POST['surname'];

                if (isset($_POST['password'])){

                    $password = $_POST['password'];

                    if (!(strlen($password) > 200)) {

                        if (isset($_POST['type'])){

                            $type = $_POST['type'];

                            if($type == 'admin' || $type == 'user'){

                                if (file_exists( './databse.xml')) {


                                    // $xml = new DOMDocument();
                                    // $xml->load("./databse.xml");
                                    $xml = simplexml_load_file("databse.xml");
                                    // $xpath = new DOMXPath($xml);
                                    $entries = $xml->users->user;
                                    

                                    $email_exist = false;

                                
                                    foreach ($entries as $entry) {
                                        // echo $entry->nodeValue;
                                        // echo $email;
                                        // var_dump($entry);
                                        if ($entry->email==$email){
                                            $email_exist = true;
                                            break;
                                        }
                                    }

                                    if ($email_exist == false){
                                       $newUser = $xml->users->addChild('user');
                                       $newUser->addChild('email',$email);
                                       $newUser->addChild('name',$name);
                                       $newUser->addChild('surname',$surname);
                                       $newUser->addChild('password',$password);
                                       $newUser->addChild('type',$type);

                                       file_put_contents("databse.xml",$xml->asXML());

                                       
                                       echo json_encode(array("success" => true, "message" => "Register completed succesful"), JSON_FORCE_OBJECT);
                                       http_response_code(200);

                                    }else{
                                        
                                        echo json_encode(array("success" => false, "message" => "Email already exist"), JSON_FORCE_OBJECT);
                                        http_response_code(400);
                                    }


                                } else {
                                    
                                    echo json_encode(array("success" => false, "message" => "XML file does not exist"), JSON_FORCE_OBJECT);
                                    http_response_code(400);
                                    
                                }

                            }else{
                               
                echo json_encode(array("success" => false, "message" => "Wrong type"), JSON_FORCE_OBJECT);
                 http_response_code(400);
                            }
                        }else{
                           
                echo json_encode(array("success" => false, "message" => "Type does not exist"), JSON_FORCE_OBJECT);
                         http_response_code(400);
                            
                        }

                    }else{
                echo json_encode(array("success" => false, "message" => "Password length over 200"), JSON_FORCE_OBJECT);
                 http_response_code(400);
                    }
                }else{
                    
                echo json_encode(array("success" => false, "message" => "Password does not exist"), JSON_FORCE_OBJECT);
                http_response_code(400);
                }

            }else{
                
                echo json_encode(array("success" => false, "message" => "Surname does not exist"), JSON_FORCE_OBJECT);
            http_response_code(400);
                
            }
            
        }else{
           
        echo json_encode(array("success" => false, "message" => "Name does not exist"), JSON_FORCE_OBJECT);
         http_response_code(400);
        }

    }else{

        
        echo json_encode(array("success" => false, "message" => "Invalid email format, email mast have the format of something@something.something"), JSON_FORCE_OBJECT);
        http_response_code(400);
        
        
    }
}else{
    
    echo json_encode(array("success" => false, "message" => "Email does not exist"), JSON_FORCE_OBJECT);
    http_response_code(400);

}