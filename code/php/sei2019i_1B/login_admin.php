<?php

include 'connection.php';

$request = json_decode(file_get_contents('php://input'), true);

$id = $request['id'];
$password = $request['password'];

$query = "SELECT * FROM administrator WHERE id = \"$id\" AND password = \"$password\"";

$result_adm = $connection -> query($query);

if(mysqli_num_rows($result_adm) == 0){

    $result_adm -> close();
    echo json_encode(array('error' => 0));
    exit();
}

$response = array();

$response["admin"] = array();

$fields = array("id", "name", "password", "limit_date");

$encoded_result = array_map('utf8_encode', $result_adm -> fetch_array());

foreach($fields as $field){

    $response["admin"][$field] = $encoded_result[$field];
}

$query = "SELECT name FROM country WHERE admin_id = \"$id\"";

$result_count_selected = $connection -> query($query);

$response["admin"]["countries"] = array();

while($country_row = $result_count_selected -> fetch_array()){
    
    $response["admin"]["countries"][] = utf8_encode($country_row["name"]);
}

$query = "SELECT name FROM country WHERE admin_id != \"$id\" OR admin_id IS NULL";

$result_count_unsel = $connection -> query($query);

while($country_row = $result_count_unsel -> fetch_array()){
    
    $response["countries"][] = utf8_encode($country_row["name"]);
}

echo json_encode($response);
$result_adm -> close();
$result_count_selected -> close();
$result_count_unsel -> close();

?>
