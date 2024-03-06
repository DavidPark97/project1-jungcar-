<?php

$pos = $_POST['pos'];
$url = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?request=coordsToaddr&coords=".$pos."&sourcecrs=epsg:4326&orders=addr,admcode,roadaddr&output=json";
$headers = array();
$headers[] = "X-NCP-APIGW-API-KEY-ID:qefbelztmt";
$headers[] = "X-NCP-APIGW-API-KEY:bBvrc3u2F54LsMtpi7ntLKRNBWLcrRzNxjhAA11a";

$ch = curl_init();
curl_setopt($ch,CURLOPT_URL,$url);
curl_setopt($ch,CURLOPT_HEADER,false);
curl_setopt($ch,CURLOPT_HTTPHEADER,$headers);
curl_setopt($ch,CURLOPT_RETURNTRANSFER,true);
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "GET");
$result = curl_exec($ch);
curl_close($ch);

$data = json_decode($result,1);
$sido = $data['results'][0]['region']['area1']['name'];
$gugun = $data['results'][0]['region']['area2']['name'];
$dong = $data['results'][0]['region']['area3']['name'];
$li = $data['results'][0]['region']['area4']['name'];
$landType = $data['results'][0]['land']['type'];
$landTypeTxt="";
if($landType=="2"){
    $landTypeTxt ="산";
}
$jibun1 = $data['results'][0]['land']['number1'];
$jibun2 = $data['results'][0]['land']['number2'];

if ($jibun1){
    $jibun = ($jibun2) ? $landTypeTxt.$jibun1."-".$jibun2 : $landTypeTxt.$jibun1;
}

$rp_address = $sido." " .$gugun. " ". $dong. " " .$li . " ".$jibun;
$arr= array("address"=>$rp_address);
$arr2=array($arr);
$jsonData=json_encode(array("webnautes"=>$arr2), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo $jsonData;


?>
