#coding:utf-8
import tensorflow as tf
import numpy as np

#格式化输出
print("hello,%s" % "world")

"""
#创建一个2*2的二维矩阵,且每个都是1
a = [[1,1],[1,1]]
#print(a)

#创建一个3*10的二维全一矩阵
b = np.ones((3,10),dtype=np.int16)
#print(b)


#生成一个3行4列的标准正太分布的矩阵
c = np.random.normal(0,1,(3,2))
#print(c)

#准备数据集
x = np.random.normal(0,1,(1,10),)
y = x*3+1+np.random.normal(0,0.005)
"""

#搭建神经网络
#numpy库中具有正态分布的函数
#w = np.random.normal(0,1,(1,1))
#b = np.random.normal(0,1,(1,1))

#numpy和tensorflow中都有float32,int16之类的变量

"""
w = tf.Variable(4,dtype=tf.float32)
t = tf.Variable(3,dtype=tf.float32)
b = tf.constant(2,dtype=tf.float32)
sess=tf.Session()
sess.run(tf.global_variables_initializer())
print(sess.run(w))
print(sess.run(b))
loss = w-b+t
train = tf.train.GradientDescentOptimizer(0.2).minimize(loss)
for i in range(1,100):
	sess.run(train)
	print("loss=",sess.run(loss),"w=",sess.run(w),"b=",sess.run(b),"t=",sess.run(t))
"""
#写法 tf.Variable(1.) 与 tf.Variable(1,dtype=tf.float32)是一样的
#对于优化来说，每个变量都会朝着让loss最小的方向而去,学习效率如果为正的，那么就会想办法缩小二者的差距，否则就会增大二者的差距，因为这是一个梯度下降的优化器
"""
x = tf.Variable(1,dtype=tf.float32)
y = tf.Variable(2.)
loss=x+y
train = tf.train.GradientDescentOptimizer(-0.5).minimize(loss)
sess = tf.Session()
sess.run(tf.global_variables_initializer())
for i in range(100):
	sess.run(train)
	print("loss=%lf"%sess.run(loss))
"""


#对接下这个例子，使用梯度下降优化器，会遇到各种各样的问题
# 1.若学习率为0，初始值为4，那么在该优化器的影响下,y不发生任何变化
# 2.若学习率为0.001，初始值为4，那么在100步以内，y会下降到10.72
# 3.若学习率为0.2,初始值为4.在10步之内，y会下降到0.001以下，最后归为0
# 在以上这个例子中，使用不同的学习率作优化，可能发生震荡现象，缓速现象
"""
x = tf.Variable(4.)
y = x*x
train = tf.train.GradientDescentOptimizer(0.2).minimize(y)
sess = tf.Session()
sess.run(tf.global_variables_initializer())
for step in range(100):
	sess.run(train)
	print("now is %d y="%(step+1),sess.run(y))
"""


#接下来这个例子 y=|x|，与上面的例子y=x*x 很像，y都有自己的最小值
# 但是若学习率为1，却不会发生上面的情况，而是一步一步的慢慢下降，并且下降步长=学习率
#但是如果为100，就会在97和3之间发生震荡
"""
x = tf.Variable(3.0)
y = tf.abs(x)

#if y>0:
#	y=x*3
#else :
#	y=-x
sess = tf.Session()
train = tf.train.GradientDescentOptimizer(100).minimize(y)
sess.run(tf.global_variables_initializer())
for i in range(0,100):
	sess.run(train)
	print("loss",sess.run(y))
"""


#如果x为tensorflow的对象，那么y就不能对它进行迭代
#对于迭代，还不是很清楚
#对于占位符，更是无法理解
"""
x = np.random.normal(0,1,(1,1))
y = 3*x+1
x = tf.constant(x)
y = tf.constant(y)
rmd = np.random.RandomState(23455)
#x= rmd.rand(32,1)
#y = 3*x


xp = tf.placeholder(tf.float32,shape=(1,1))
yp = tf.placeholder(tf.float32,shape=(1,1))
w = tf.Variable(1.)
b =  tf.Variable(1.)
y_ = xp*w+b
print(y_)

print(x.shape)
print(y)

loss = tf.reduce_mean(tf.square(yp-y_))
train = tf.train.GradientDescentOptimizer(0.01).minimize(loss)
sess = tf.Session()
sess.run(tf.global_variables_initializer())

for step in range(10000):
	if step % 20==0 :
		sess.run(train,{xp:x,yp:y})
		print("now is %d"%step,sess.run(loss))
		
"""


"""
#sess.run套着sess.run会报TypeError: input must be a dictionary
rmd = np.random.RandomState(233)
x= rmd.rand(100,1)
y = [x1 for (x1) in x]
y = 2*x+3

xp = tf.placeholder(tf.float32,shape=(None,1))
yp = tf.placeholder(tf.float32,shape=(None,1))
w = tf.Variable(tf.random.normal([1,1],1,1))
b = tf.Variable(tf.random.normal([1,1],1,1))
y_ = tf.matmul(xp,w)+b
loss = tf.reduce_mean(tf.square(y_-yp))
train = tf.train.GradientDescentOptimizer(0.003).minimize(loss)
sess = tf.Session()
sess.run(tf.global_variables_initializer())
for i in range(40000):
	sess.run(train,feed_dict={xp:x,yp:y})
	if i%500==0:
		print("loss=",sess.run(loss,feed_dict={xp:x ,  yp:y  }))
		print(sess.run(w))
		print(sess.run(b))
"""

#对于 y=x**3+1 使用梯度下降法难以得到最优解
# 因为 优化参数最多从 y=kx对它进行影响?
"""
x= np.random.normal(1,0.5,(100,1))
#y= x*x*x+1
y = (x**3) + 1
xp = tf.placeholder(tf.float32,(None,1))
yp = tf.placeholder(tf.float32,(None,1))
w = tf.Variable(tf.random.normal((1,1),0,1))
#b = tf.Variable(tf.random.normal((1,1),0,1))
y_ = tf.matmul(xp,w)

w2 = tf.Variable(tf.random.normal((1,1),0,1))
#b2 = tf.Variable(tf.random.normal(1,1),0,1)
yy_ = tf.matmul(y_,w2)

w3 = tf.Variable(tf.random.normal([1,1],0,1))
yyy_ = tf.matmul(yy_,w3)

loss = tf.reduce_mean(tf.square(yyy_-yp))
train = tf.train.GradientDescentOptimizer(0.00001).minimize(loss)
sess = tf.Session()
sess.run(tf.global_variables_initializer())
for i in range(100000):
	sess.run(train,feed_dict={xp:x,yp:y})
	if i%500==0:
		print("after %d step:" % i)
		print("loss is ",sess.run(loss,feed_dict={xp:x,yp:y}))
		print(sess.run(w))
		print(sess.run(w2))
		print(sess.run(w3))
"""













