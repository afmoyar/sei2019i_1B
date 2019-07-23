<?php

include 'connection.php';

$request = json_decode(file_get_contents('php://input'), true);

//$id = $request['id'];
//$password = $request['password'];

$id = "afmoyar";
$password = "1234";

$query = "SELECT * FROM user WHERE id = \"$id\" AND password = \"$password\"";

$result_usr = $connection -> query($query);

if(mysqli_num_rows($result_usr) == 0){

    $result_usr -> close();
    echo json_encode(array('error' => 0));
    exit();
}

$user = array();

$fields = array("id", "name", "password");

$encoded_result = array_map('utf8_encode', $result_usr -> fetch_array());

foreach($fields as $field){

    $user[$field] = $encoded_result[$field];
}

$query = "SELECT * FROM place INNER JOIN user_place ON place.latitude = user_place.place_latitude AND place.longitude = user_place.place_longitude AND user_place.user_id = \"$id\"";

$result_place = $connection -> query($query);

$fields = array("longitude", "latitude", "name", "description", "country_name", "comment", "rating");

$user["places"] = array();

while($place_row = $result_place -> fetch_array()){

    $place = array();

    foreach($fields as $field){
    
        $place[$field] = utf8_encode($place_row[$field]);
    }
    
    $user["places"][] = $place;
}

$season_countries = array();

$query = "SELECT name FROM country WHERE admin_id IS NOT NULL";

$result_countries = $connection -> query($query);

while($country_row = $result_countries -> fetch_array()){

    $season_countries[] = utf8_encode($country_row["name"]);
}

$query = "SELECT limit_date FROM administrator WHERE id IS NOT NULL";

$result_date = $connection -> query($query);

$limit_date = $result_date -> fetch_array();

$limit_date = utf8_encode($limit_date["limit_date"]);


$response = array('user' => $user, 
                  'season_countries' => $season_countries,
                  'limit_date' => $limit_date);

echo json_encode($response);
$result_usr -> close();
$result_place -> close();
$result_countries -> close();
?>
