<?php

include 'connection.php';
$id=$_GET['id'];
$password=$_GET['password'];

$consulta = "SELECT * FROM administrator WHERE id = '$id' AND password = '$password'";
$resultado = $connection -> query($consulta);

while($fila=$resultado -> fetch_array()){
    $administrator[] = array_map('utf8_encode',$fila);
}

echo json_encode($administrator);
$resultado -> close();

?>