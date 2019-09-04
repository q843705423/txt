s = input("").split()
a = int(s[0])
b = int(s[1])
c = int(s[2])
d = int(s[3])
e = (60*c + d) - (60*a + b)
h = int(e/60)
m = e%60
print( str(h) + " " + str(m))
