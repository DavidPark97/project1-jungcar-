<?php
header('Content-Type: text/html; charset=UTF-8');

$con=mysqli_connect("localhost","root","1234","jungcar");
//파일 img라는 파일을 받아온다 (안드로이드로 부터)

$img = $_POST['img'];
$user_idx = $_POST['user_idx'];
//서버에 저장할 파일내용
$filename = "search/".$user_idx.".jpg";
file_put_contents($filename,base64_decode($img));
$tmp = $user_idx."jpg";
$locale='de_DE.UTF-8';
setlocale(LC_ALL,$locale);
putenv('LC_ALL='.$locale);
$command = escapeshellcmd("carpredict.py $tmp");
$output = shell_exec($command);
$output = iconv("EUC-KR","UTF-8",$output);
$plnumber = explode(" ",$output);
$text=$plnumber[0];
$text = str_replace(array("\r\n","\r","\n"),'',$text);


$query = "select count(*) as cnt from car_market, car, model, connection, fuel 
where car_market.car_idx = car.car_idx and model.model_idx = connection.model_idx 
and connection.connection_idx = car.connection_idx and car.fuel_idx = fuel.fuel_idx and car_market.sell_idx not in (select sell_idx from trade) and year like '%$text%'";

$result = mysqli_query($con, $query);
$count = mysqli_fetch_array($result);
$cnt = $count['cnt'];

$arr= array("name"=>$text,"cnt"=>$cnt);

if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();
$arr2=array($arr);
$jsonData=json_encode(array("webnautes"=>$arr2), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo $jsonData;


?>