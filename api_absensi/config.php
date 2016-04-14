<?php 
error_reporting(E_ALL ^ E_DEPRECATED);
# FileName="Connection_php_mysql.htm"
# Type="MYSQL"
# HTTP="true"
	$user = "root";
	$pass = "";
	$ip = "127.0.0.1";
	$DB = "absensi";
    
    $konek = mysql_connect($ip,$user,$pass) ;
    $pilihDB  = mysql_select_db($DB,$konek);
 	if (! $konek) {
 			echo "Server tidak di temukan";
 	}
 	if (! $pilihDB) {
 			echo "Database Tidak Ditemukan";
 	}
 ?>