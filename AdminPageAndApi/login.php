<?php

if(isset($_POST['type'])){

    $type = $_POST['type'];
    if($type == 'admin' || $type == 'user'){
        if (isset($_POST['email'])){
            $email = $_POST['email'];
            
            if (filter_var($email, FILTER_VALIDATE_EMAIL)) {
                
                if (isset($_POST['password'])){
                    $password = $_POST['password'];
        
                    if (file_exists( './databse.xml')) {
        
                        $xml = simplexml_load_file("databse.xml");
                        $entries = $xml->users->user;
                        
                        $flag=false;
                        
                        foreach ($entries as $entry) {
                            //echo "manasou2";
                            //echo $entry->email;
                            //echo $entry->password;
                            // echo $email;
                            //var_dump ($entry->email==$email);
                            if ($entry->email==$email){
                                $flag=true;
                                if($entry->password==$password){
                                    if($entry->type=='admin' ||$entry->type==$type)
                                    {
                                        echo json_encode(array("success" => true, "message" => "Log In completed succesful" ), JSON_FORCE_OBJECT);
                                        http_response_code(200);
                                        
                                    }else{
                                        echo json_encode(array("success" => false, "message" => "You don't have the privillege to login here"), JSON_FORCE_OBJECT);
                                http_response_code(400);
                                break;
                                    }
                                }else{
                                    echo json_encode(array("success" => false, "message" => "Wrong Password"), JSON_FORCE_OBJECT);
                                http_response_code(400);
                                break;
                                }
                            }
                        }
                        if(!$flag){
                                echo json_encode(array("success" => false, "message" => "Incorect Email"), JSON_FORCE_OBJECT);
                                http_response_code(400);
                            }
                        
                    }else{   
                        
                        echo json_encode(array("success" => false, "message" => "XML File does not exist"), JSON_FORCE_OBJECT);
                        http_response_code(400);
                    }
        
                }else{
                    
                    echo json_encode(array("success" => false, "message" => "Password does not exist"), JSON_FORCE_OBJECT);
                    http_response_code(400);
                }
            }
            else {
                echo json_encode(array("success" => false, "message" => "Invalid email format, email mast have the format of something@something.something"), JSON_FORCE_OBJECT);
                http_response_code(400);
            }
        
        }else{
            echo json_encode(array("success" => false, "message" => "Email does not exist"), JSON_FORCE_OBJECT);
            http_response_code(400);
        }
    }else{
        echo json_encode(array("success" => false, "message" => "Wrong type"), JSON_FORCE_OBJECT);
        http_response_code(400);
    }
}else {
    echo json_encode(array("success" => false, "message" => "Type does not exist"), JSON_FORCE_OBJECT);
    http_response_code(400);
}