<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

//파일 img라는 파일을 받아온다 (안드로이드로 부터)
$img = $_POST['img'];
$flag = $_POST['flag'];
$market_idx = $_POST['market_idx'];
//서버에 저장할 파일내용
$filename = "part/pr".$market_idx."_".$flag.".jpg";
file_put_contents($filename,base64_decode($img));



if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();


if($flag==="0"){
    $result=mysqli_query($con, "update part_market set img = '$filename' where market_idx = $market_idx");
}else{
    $result=mysqli_query($con, "insert into part_img (market_idx,img) values ($market_idx,'$filename')");

}


?>