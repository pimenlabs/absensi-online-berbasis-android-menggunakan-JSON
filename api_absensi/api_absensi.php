<?php
header("Content-Type:application/json");
include "config.php";
if (isset($_GET['idk']) ) {
	
$idk = htmlspecialchars(mysql_real_escape_string($_GET["idk"]));

$query = "select * from siswa where idk='$idk'  ";

$hasil = mysql_query($query);
if (mysql_num_rows($hasil) > 0) {
$response = array();
$response["siswa"] = array();
while ($data = mysql_fetch_array($hasil))
{
$h['ids']  	= $data['ids'];
$h['nis']  	= $data['nis'];
$h['nama']  		= $data['nama'];
 array_push($response["siswa"], $h);
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
}else {
    $response["success"] = "0";
    $response["message"] = "Tidak ada data";
	header('Content-type: application/json; charset=utf-8');
	echo json_encode($response);
}
?>