n = input("请输入n")
n = int(n)
a = [0] * 10
for i in range(0,n):
	a[i] =  input("")
	a[i] = int(a[i])
s = 0
for i in range(0,n):
	s = s + a[i]
print(s)