import tensorflow as tf
import numpy as np
SEED = 23455
BATCH_SIZE = 8
COST = 9
PROFIT = 1
rmd = np.random.RandomState(SEED)
#X= rmd.rand(32,2)
#Y_ = [[x1+x2+rmd.rand()/10.0-0.05] for (x1,x2) in X]
X = np.random.normal(0,1,(32,1))
Y_ = [[x1] for (x1) in X]
print(X)
print("--------")
print(Y_)
x = tf.placeholder(tf.float32,shape=(None,1))
y_ = tf.placeholder(tf.float32,shape=(None,1))
w1 = tf.Variable(tf.random_normal([1,1],stddev=1,seed=1))
y = tf.matmul(x,w1)
loss_mes = tf.reduce_mean(tf.square(y_-y))
#loss_mes = tf.reduce_sum(tf.where ( tf.greater(y,y_) , (y-y_) ,(y_-y)))

train_step = tf.train.GradientDescentOptimizer(0.001).minimize(loss_mes)

with tf.Session() as sess:
    init_op = tf.initialize_all_variables()
    sess.run(init_op)
    STEPS = 20000
    for i in range(STEPS):
        st = (i*BATCH_SIZE)%32
        en = (i*BATCH_SIZE)%32+BATCH_SIZE
        sess.run(train_step,feed_dict={x: X[st:en],y_: Y_[st:en]})
        if i%500==0:
            print ("after %d training steps ,w1 =" %(i))
            #print (sess.run(w1),"\n")
#    print "final w=",sess.run(w1)
