#!"C:\Users\ssss\AppData\Local\Programs\Python\Python38\python.exe"
# -*- coding: utf-8 -*-
"""Untitled0.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1bphiJVM6ACFRcRn-TG-BmZeHJpLpb_8Z
"""
from xmlrpc.client import boolean
from tensorflow.keras import layers, models
import numpy as np
from PIL import Image
import matplotlib.pyplot as plt
import matplotlib.image as img
from keras.models import load_model
import sys

#모델 임포트
model = models.Sequential([
    layers.Conv2D(32, kernel_size=(5, 5), strides=(1, 1),
                  padding='same', activation='relu', input_shape=(28, 28, 1)),
    layers.MaxPooling2D(pool_size=(2, 2), strides=(2, 2)),
    layers.Conv2D(64, kernel_size=(2, 2), activation='relu', padding='same'),
    layers.MaxPooling2D(pool_size=(2, 2)),
    layers.Dropout(0.25),
    layers.Flatten(),
    layers.Dense(1000, activation='relu'),
    layers.Dense(10, activation='softmax')
])

# 이미지를 숫자로
def image_to_array(image_path, size):
    # open image
    image = Image.open(image_path)      
    # resize (클수록 선명하지만 느릴 수 있다)
    image = image.resize((size, size))  
    # convert to int ndarray
    im_arr = np.fromstring(image.tobytes(), dtype=np.uint8)  
    # 모양확인 변수 .shape
    
    
    # reshape to (28, 28, 3)
    im_arr = im_arr.reshape((image.size[0], image.size[1], 3)) 
    
    
    # 흑백 반전, 색이 클수록 밝으므로 -1을 곱하고 255더하면 해당차원(R or G or B 색조의) 밝은곳은 어두워지고, 어두운곳은 밝아짐 
    # (ex 완전한 초록색은 정반대의 색깔인 완전한 노란색)
    im_arr = im_arr*(-1)+255   
    return im_arr

# convert to 1-channel by averaging
def image_array_to_1channel(arr):
    arr_avg = (arr[:,:,0]+arr[:,:,1]+arr[:,:,2])/3   
    return arr_avg

def predictnum(path):

  # 1. 그림->숫자 함수 확인
  arr = image_to_array(path, 28)
  plt.imshow(arr, cmap=plt.get_cmap('gray'))

# 2. 1채널화 함수 확인

  arr_1c = image_array_to_1channel(arr) #원채널로



  img_rows, img_cols = 28, 28
  x_train = arr_1c.reshape(1, img_rows, img_cols, 1) #CNN모델에 적용할수 있게


  y_prob = model.predict(x_train, verbose=0) 
  predicted = []
  img_test = img.imread(path)

# 이미지 파일 -> 1차원 리스트 -> (28 , 28)

  arr = image_to_array(path, 28)
  arr_1c = image_array_to_1channel(arr) 

  img_rows, img_cols = 28, 28
  x_train = arr_1c.reshape(1, img_rows, img_cols, 1)
  Spredict = ""
    
  result = model.predict(x_train, verbose=0)
  res = result.argmax(axis=1)
  Spredict += str(res[0])

  return(Spredict)

def predictkor(path):

  img = Image.open(path)#.convert("L")
  img = np.resize(img, (1,28,28,3))
# 데이터를 모델에 적용할 수 있도록 가공
  test_data = ((np.array(img) / 255) - 1) * -1


# 클래스 예측 함수에 가공된 테스트 데이터 넣어 결과 도출
  res = model.predict(test_data)
#res =(model.predict(test_data) > 0.5).astype("int32")
  Spredicted = res.argmax(axis=-1)
  if Spredicted == 0 :
      Spredicted = '가'
  if Spredicted == 1 :
    Spredicted = '거'
  if Spredicted == 2 :
    Spredicted = '고'
  if Spredicted == 3 :
      Spredicted = '구'
  if Spredicted == 4 :
    Spredicted = '나'
  if Spredicted == 5 :
    Spredicted = '너'
  if Spredicted == 6 :
      Spredicted = '노'
  if Spredicted == 7 :
    Spredicted = '누'
  if Spredicted == 8 :
    Spredicted = '다'
  if Spredicted == 9 :
      Spredicted = '더'
  if Spredicted == 10 :
    Spredicted = '도'
  if Spredicted == 11:
    Spredicted = '두'
  if Spredicted == 12 :
      Spredicted = '라'
  if Spredicted == 13 :
    Spredicted = '러'
  if Spredicted == 14 :
    Spredicted = '로'
  if Spredicted == 15 :
      Spredicted = '루'
  if Spredicted == 16 :
    Spredicted = '마'
  if Spredicted == 17 :
    Spredicted = '머'
  if Spredicted == 18 :
      Spredicted = '모'
  if Spredicted == 19 :
    Spredicted = '무'
  if Spredicted == 20 :
    Spredicted = '버'
  if Spredicted == 21 :
      Spredicted = '보'
  if Spredicted == 22 :
    Spredicted = '부'
  if Spredicted == 23 :
    Spredicted = '서'
  if Spredicted == 24 :
      Spredicted = '소'
  if Spredicted == 25 :
    Spredicted = '수'
  if Spredicted == 26 :
    Spredicted = '어'
  if Spredicted == 27 :
      Spredicted = '오'
  if Spredicted == 28 :
    Spredicted = '우'
  if Spredicted == 29 :
    Spredicted == '저'
  if Spredicted == 30 :
      Spredicted = '조'
  if Spredicted == 31 :
    Spredicted = "주"

  return Spredicted

def predict(a, b) :
  z = False

  f=open("C:\\apache24\\htdocs\\test.txt",'w')
  #model = load_model('C:\\apache24\\htdocs\\num_model.h5')
  predict = ""
  for i in range(b):
    if i==2:
        continue
    # 사용할 모델 로드
    if b == 7 :
        if i == 3 : 
           model = load_model('C:\\apache24\\htdocs\\Kor_Model.h5')
           z = True
        else : 
           model = load_model('C:\\apache24\\htdocs\\num_model.h5')
           z = False
    else : 
      if i == 4 : 
         model = load_model('C:\\apache24\\htdocs\\Kor_Model.h5')
         z = True
      else : 
         model = load_model('C:\\apache24\\htdocs\\num_model.h5')
         z = False
    tpath = ["C:\\apache24\\htdocs\\plnumber\\"+str(a)+"_"+str(i)+".jpg"]
    path = ''.join(str(s) for s in tpath)

    if z == True : 
      predict = predictkor(path)

    else :
      predict = predictnum(path)
   

   


  
  f.close()
  return(predict)

tmp = predict(int(sys.argv[1]), int(sys.argv[2]))
print(tmp)
