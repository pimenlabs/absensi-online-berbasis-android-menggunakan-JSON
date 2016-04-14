<?php 

header("Content-Type:application/json");
include "config.php";


if (isset($_POST['req']) ) {
    # code...

    $get = json_decode(stripslashes($_POST['req']));

    $ids = $get->ids;
    $idm = $get->idm;
    $idk = $get->idk;
    $tgl = date("Y/m/d") ;
    $jam = date("h:i:sa");

    $soalessay = mysql_query("select * from siswa where idk='$idk'  ");
    $tot = mysql_num_rows($soalessay);

    for ($i=0; $i < $tot; $i++) { 
        
         $ket[$i] = $get->{'ket'.$i};

    }


    for ($urut=0; $urut < $tot ; $urut++) { 
          
            mysql_query("INSERT INTO absensi(ida,ids,idm,tgl,ket,jam)
                                       VALUES(null,'".$ids."',
                                        '".$idm."','".$tgl."',
                                        '".$ket[$urut]."',
                                        '".$jam."'

                                        )");
           
       

    }

   
     $response["success"] = "1";
        //the JSON message
      header('Content-type: application/json; charset=utf-8');
      echo json_encode($response);
 }else {
         $response["success"] = "0";
          $response["message"] = "Tidak ada data";
//        echo json_encode($response);

        //the JSON message
        header('Content-type: application/json; charset=utf-8');
        echo json_encode($response);

}
 ?>
