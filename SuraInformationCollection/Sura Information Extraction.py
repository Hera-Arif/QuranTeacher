print("Bismillahir Rahmanir Rahim")
file="C:\\Users\\Rahat\\Documents\\List of Quranic suras - Wikipedia, the free encyclopedia.txt"
fileSub="E:\\CSE 2-1 projects\\My project\\Resource\\res\\text\\metadata\\quran-properties.xml"

f=open(file,"r",encoding="UTF-8")
fSub=open(fileSub,"r",encoding="UTF-8")
s=f.readlines()
sSub=fSub.readlines()

ArabicTitle=[]
EnglishMeaning=[]

for i in range(len(sSub)):
    if(sSub[i].count("sura index")>0):
        j=sSub[i].index("tname=")
        k=sSub[i].index('"',j+7)
        string=sSub[i][j+7:k]
        ArabicTitle.append(string)

        j=sSub[i].index("en=")
        k=sSub[i].index('"',j+4)
        string=sSub[i][j+4:k]
        EnglishMeaning.append(string)
        

print("#")
print("Arabic Title")
print("English Title")
print("Number of verses")
print("Meccan vs Median")
print("Egyptian Standard Chronological Order")
print("Noldeke's chronological order")
print("Title refers to")
print("Main theme")

writingFile="C:\\Users\\Rahat\\Documents\\sura information.txt"

try:
    f=open(writingFile,"w")
    f.close()
except:
    pass

f=open(writingFile,"a")

English=["God",
         "Abraham",
         "Moses",
         "Noah",
         "Jonah",
         "Joseph",
         "Jesus",
         "Lot",
         "Muhammad",
         "Adam",
         "Eve",
         "Hud",
         "Salih",
         "Musa",
         "Zachariah",
         "John",
         "Shuáyb",
         "Mary",
         "king Solomon",
         "queen of Sheba",
         "Luqman",
         "Prophet",
         "Meccan",
         "Median"
        ]
Arabic=["Allah",
        "Ibraheem(PBUH)",
        "Musa(PBUH)",
        "Nouh(PBUH)",
        "Yunus(PBUH)",
        "Yusuf(PBUH)",
        "Isa(PBUH)",
        "Lut(PBUH)",
        "Muhammad(PBUH)",
        "Adam(PBUH)",
        "Eve(AS)",
        "Hud(PBUH)",
        "Salih(PBUH)",
        "Musa(PBUH)",
        "Zakariah(PBUH)",
        "Yahya(PBUH)",
        "Shuáyb(PBUH)",
        "Maryam(AS)",
        "Sulaiman(PBUH) [king Solomon]",
        "Bilqis(AS) [queen of Sheba]",
        "Luqman(PBUH)",
        "Prophet(PBUH)",
        "Makki",
        "Madani"
        ]
names=[
"id",
"ar",
"en",
"ac",
"dc",
"ro",
"no",
"re",
"mt"]

termination=['.',',','(',')','[',']',"'","’",'-',' ','=','\n']
def edit(old):
    new=''
    length=len(old)

    i=0
    changed=False
    while(i<length):
        p=''
        j=i
        while(j<length):
            if old[j] in termination:
                #p=old[i:j]
                if p in English:
                    print(p,"-->",end='')
                    p=Arabic[English.index(p)]
                    print(p)
                    changed=True
                    
                p+=old[j]
                i=j
                break
            else:
                p+=old[j]

            j+=1

        new+=p
        i+=1
    #if(changed):
        #print(new)
    return new
    

print('-----------------------')
level=0
count=0
for i in range(len(s)):
    if(len(s[i]))>5:
        if(s[i][0:3]=="<td" or s[i][0:3]=="<li"):
            if(level==6):#Exclude Noldocs Order
                level=7
                continue
            elif(s[i][1]=='l'):
                level=8
            elif(level==0):
                f.write('\n')
            result=names[level]+'='
            if(level>2):
                j=4
                while(j<len(s[i])):
                    if(s[i][j]=='<'):
                        if(s[i][j+1:j+4]=='sup'):
                            j=s[i].find('/sup>',j)
                            j+=5
                            continue
                        else:
                            j=s[i].find('>',j)
                            j+=1
                            continue

                    result+=s[i][j]
                    j+=1

                result=edit(result)
            elif level==0:
                result+=str(count+1)+'\n'
            elif level==1:
                result+=ArabicTitle[count]+'\n'
            elif level==2:
                result+=EnglishMeaning[count]+'\n'
                count+=1
                
            #result+='\n'
            #print(result)
            
            f.write(result)    
            level+=1
            level%=9
f.close()
print("finished")

        
