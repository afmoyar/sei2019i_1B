<?php

include 'connection.php';
$id=$_GET['id'];

$consulta = "SELECT latitude, longitude, name, description, country_name FROM place,user_place WHERE user_place.user_id = '$id' AND place.latitude = user_place.place_latitude AND user_place.place_longitude = place.longitude";
$resultado = $connection -> query($consulta);

while($fila=$resultado -> fetch_array()){
    $places[] = array_map('utf8_encode',$fila);
}

echo json_encode($places);
$resultado -> close();

?>