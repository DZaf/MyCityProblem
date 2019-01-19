<?php

if(isset($_GET['voteType'])){

    $vote = $_GET['voteType'];

    if(isset($_GET['email'])){

        $email = $_GET['email'];

        if(isset($_GET['id'])){

            $id = $_GET['id'];

            if($vote === 'upVote' || $vote === 'downVote'){

                if (filter_var($email, FILTER_VALIDATE_EMAIL)) {
                    
                    if(!($id === '')){

                        if (file_exists( './databse.xml')) {

                            $xml = simplexml_load_file("databse.xml");
                            $entries = $xml->events->event;
                            $email_exist = false;

                        if($vote === 'upVote'){

                            foreach ($entries as $entry) {
                                if ($entry['id']==$id){
                                    
                                    for($x=0;$x<=$entry->votes->upVotes->email->count();$x++)
                                    {
                                        if ($entry->votes->upVotes->email[$x] == $email){
                                                    $email_exist = true;
                                                    break; }
                                    }

                                }

                                if($email_exist === true)
                                break;
                               
                            }
                            if($email_exist == false){

                            foreach ($entries as $entry) {

                                if ($entry['id']==$id){
                                    $entry->votes->upVotes->addChild('email',$email);
                                    file_put_contents("databse.xml",$xml->asXML());
                                    http_response_code(200);
                                    echo json_encode(array("success" => true, "message" => "upVote success"), JSON_FORCE_OBJECT);
                                    break;
                                }
                            }
                        }else{
                            http_response_code(400);
            echo json_encode(array("success" => false, "message" => "Vote already given"), JSON_FORCE_OBJECT);
                        }

                        }
                         if($vote === 'downVote'){

                            foreach ($entries as $entry) {
                                if ($entry['id']==$id){
                                    for($x=0;$x<=$entry->votes->downVotes->email->count();$x++)
                                    {
                                        if ($entry->votes->downVotes->email[$x] == $email){
                                                    $email_exist = true; 
                                                    break; }
                                    }
                                   
                                }
                                if($email_exist === true)
                                break;
                               
                               }
                            

                            if($email_exist == false){

                            foreach ($entries as $entry) {
                                if ($entry['id']==$id){
                                    $entry->votes->downVotes->addChild('email',$email);
                                    file_put_contents("databse.xml",$xml->asXML());
                                    http_response_code(200);
                                    echo json_encode(array("success" => true, "message" => "downVote success"), JSON_FORCE_OBJECT);
                                    break;
                                }
                            }
                        }else{
                            http_response_code(400);
                            echo json_encode(array("success" => false, "message" => "Vote already given"), JSON_FORCE_OBJECT);
                        }
                    }
                        
                    }else{
                        http_response_code(400);
                        echo json_encode(array("success" => false, "message" => "File not exist"), JSON_FORCE_OBJECT);
                    }

                    }else{
                        http_response_code(400);
                        echo json_encode(array("success" => false, "message" => "Id cant be void"), JSON_FORCE_OBJECT);
                    }

                } else {
                  http_response_code(400);
                  echo json_encode(array("success" => false, "message" => "Wrong type of email variable"), JSON_FORCE_OBJECT);
                }
            }else{
                http_response_code(400);
                echo json_encode(array("success" => false, "message" => "Wrong vote type"), JSON_FORCE_OBJECT);
            }
        }else{
            http_response_code(400);
            echo json_encode(array("success" => false, "message" => "Id not exist"), JSON_FORCE_OBJECT);
        }
    }else{
        http_response_code(400);
        echo json_encode(array("success" => false, "message" => "Email not exist"), JSON_FORCE_OBJECT);
    }
}else{
    http_response_code(400);
    echo json_encode(array("success" => false, "message" => "Vote not exist"), JSON_FORCE_OBJECT);
}






    
        
            

                

            
        
        
    

