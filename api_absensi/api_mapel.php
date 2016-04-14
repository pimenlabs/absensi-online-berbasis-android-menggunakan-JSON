<?php
header("Content-Type:application/json");
include "config.php";


	

$query = "select * from matapelajaran ";

$hasil = mysql_query($query);
if (mysql_num_rows($hasil) > 0) {
$response = array();
$response["mapel"] = array();
while ($data = mysql_fetch_array($hasil))
{
$h['idm']  	= $data['kodemp'];
$h['nama']  		= $data['nmmp'];
 array_push($response["mapel"], $h);
}
	$response["success"] = "1";
	header('Content-type: application/json; charset=utf-8');
	echo json_encode($response);

}else {
    $response["success"] = "0";
    $response["message"] = "Tidak ada data";
	header('Content-type: application/json; charset=utf-8');
	echo json_encode($response);
}
?>