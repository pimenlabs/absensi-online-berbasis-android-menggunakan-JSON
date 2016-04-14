<?php
header("Content-Type:application/json");
include "config.php";
if ((isset($_GET['nip'])) or (isset($_GET['pass']))) {
	
$nip = htmlspecialchars(mysql_real_escape_string($_GET["nip"]));
$password = htmlspecialchars(mysql_real_escape_string($_GET["pass"]));
$password= md5($password);

$query = "select * from guru where nip='$nip' and pass='$password' ";

$hasil = mysql_query($query);
if (mysql_num_rows($hasil) > 0) {
$response = array();
$response["login"] = array();
while ($data = mysql_fetch_array($hasil))
{
$h['idg']  	= $data['idg'];
$h['nip']  		= $data['nip'];
$h['nama']  		= $data['nama'];
$h['jk']  = $data['jk'];
$h['alamat']  		= $data['alamat'];
$h['idk']  = $data['idk'];
 array_push($response["login"], $h);
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