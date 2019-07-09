<?php

include 'connection.php';

$data = json_decode(file_get_contents('php://input'), true);

$id=$data['id'];
$password=$data['password'];

$places = array(array("latitude" => "123", "longitude" => 456, "name" => "Bogota", "description" => "gran ciudad", "countryName" => "Colombia"));
$dummy = array("id" => $id, "name" => $password, 'password' => "123abc", "places" => $places);

echo json_encode($dummy);

?>
