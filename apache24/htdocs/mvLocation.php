<?php

$pos = $_POST['pos'];
$pos = urlencode($pos);
$url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=".$pos;
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
$longitude = $data['addresses'][0]['x'];
$latitude = $data['addresses'][0]['y'];
$arr= array("longitude"=>$longitude,"latitude"=>$latitude);
$arr2=array($arr);
$jsonData=json_encode(array("webnautes"=>$arr2), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo $jsonData;


?>
