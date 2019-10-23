import time
import random
import curses
import _thread

s = curses.initscr()
sh, sw = s.getmaxyx()
w = curses.newwin(sh, sw, 0, 0)



w.keypad(1)
w.timeout(200)
words = ["1", "2", "3", "4", "5", "6", "7", "8","9","0"]
wordlen = len(words)
flyword = []

goal = 0
life = 5
maxNumber = 10

myInput = ""
def input():
    pass

idx = 0

while True:
    input()

    next_key = w.getch()
    if next_key == curses.KEY_DOWN:
        curses.endwin()
        quit()


    if (next_key != -1):

        if next_key == 8:

            w.addstr(28,1,"input:"+" "*10)
            myInput = ""

        elif next_key == 10:
            maxX = -1
            realPos = -1
            pos = 0
            for oneWord in flyword:
                if oneWord["word"] == myInput:
                    if oneWord["x"] + oneWord["len"] > maxX:
                        maxX = oneWord["x"] + oneWord["len"]
                        realPos = pos

                pos += 1
            if maxX != -1:

                goal += len(myInput)
                w.addstr(28,1,"input:"+" "*10)
                myInput = ""
                maxNumber += 2
                w.addstr(flyword[realPos]["y"],flyword[realPos]["x"],flyword[realPos]["len"] * " ")
                del flyword[realPos]

            myInput = ""
            w.addstr(28,1,"input:                    ")


        elif next_key == 32:
            pass


        else:
            myInput += str(chr(next_key))
            idx += 1

    else:
        if next_key != -1:
            pass
    

    wordStr = words[random.randint(0,wordlen-1)]

    word = {
        "word":wordStr,
        "x":0,
        "y":random.randint(0,sh-8),
        "len":len(wordStr)
        }

    isOk = True
    for oneWord in flyword:
        if word["y"] != oneWord["y"]:
            pass
        if word["x"] >= oneWord["x"] and word["x"] <= oneWord["x"] + oneWord["len"]:
            isOk = False
        if word["x"] + word["len"] >= oneWord["x"] and word["x"] + word["len"] <= oneWord["x"] + oneWord["len"]:
            isOk = False

    if isOk:
        if len(flyword) < maxNumber:
            flyword.append(word)
    

    for oneWord in flyword:

        if oneWord["x"] >= sw:
            flyword.remove(oneWord)
            life -= 1
            if life <= 0 :
                curses.endwin()
        else:
            w.addstr(oneWord["y"], oneWord["x"], " " * oneWord["len"])

            oneWord["x"] = oneWord["x"] + 1

            if oneWord["x"] <  sw:
                w.addstr(oneWord["y"], oneWord["x"], oneWord["word"] )
            else:
                flyword.remove(oneWord)
                life -= 1
                if life <=0 :
                    curses.endwin()


    w.addstr(25,1,"goal:"+str(goal))
    w.addstr(26,1,"life:"+str(life))
    w.addstr(28,1,"input:"+myInput)
    if life <= 0 :
        break

print("your get goal:"+ str(goal))
