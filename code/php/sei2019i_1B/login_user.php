<?php

include 'connection.php';

$request = json_decode(file_get_contents('php://input'), true);

$id = $request['id'];
$password = $request['password'];

$query = "SELECT * FROM user WHERE id = \"$id\" AND password = \"$password\"";

$result_usr = $connection -> query($query);

if(mysqli_num_rows($result_usr) == 0){

    $result_usr -> close();
    echo json_encode(array('error' => 0));
    exit();
}

$response = array();

$fields = array("id", "name", "password");

$encoded_result = array_map('utf8_encode', $result_usr -> fetch_array());

foreach($fields as $field){

    $response[$field] = $encoded_result[$field];
}

$query = "SELECT * FROM place INNER JOIN user_place ON place.latitude = user_place.place_latitude AND place.longitude = user_place.place_longitude AND user_place.user_id = \"$id\"";

$result_place = $connection -> query($query);

$fields = array("longitude", "latitude", "name", "description", "country_name");

$response["places"] = array();

while($place_row = $result_place -> fetch_array()){

    $place = array();

    foreach($fields as $field){
    
        $place[$field] = utf8_encode($place_row[$field]);
    }
    
    $response["places"][] = $place;
}

echo json_encode($response);
$result_usr -> close();
$result_place -> close()

?>
