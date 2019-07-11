<?php
include 'connection.php';

$query = "select count(*) from country where admin <> null";
$result_count = $connection -> query($query);

if($result_count == 0){
    echo json_encode(array('error' => 0));
    exit();
}

$query = "select name from country where admin <> null";
$result_name = $connection -> query($query);
$fields = array("name");

$response["countries"] = array();

while($country_row = $result_name -> fetch_array()){

    $country = array();

    foreach($fields as $field){
    
        $country[$field] = utf8_encode($country_row[$field]);
    }
    
    $response["countries"][] = $country;
}


echo json_encode($response);
$result_count -> close();
$result_names -> close();
?>