import sqlite3 as lite
import sys, csv

uid = 0
tmp = 0

data=[]

connection = lite.connect('us_crime.db')
cursor = connection.cursor()
cursor.execute("CREATE TABLE Crime(id INT, State TEXT,City TEXT,Population INT,Violent_crime INT, Murder_and_nonnegligent_manslaughter INT, Rape INT, Robbery INT, Aggravated_assault INT, Property_crime INT,Burglary INT,Larceny_theft INT,Motor_vehicle_theft INT,Arson INT)")

creader = csv.reader(open('crime_text.csv', 'rt'), delimiter=',')
for t in creader:
	if(tmp == 0):
		tmp += 1
		continue
	data.append([uid] + t)
	if(uid < 20):
		print(data[uid])
		print(len(data[uid]))
	uid += 1


cursor.executemany("INSERT INTO Crime (id, State,City,Population,Violent_crime, Murder_and_nonnegligent_manslaughter, Rape, Robbery, Aggravated_assault, Property_crime,Burglary,Larceny_theft,Motor_vehicle_theft,Arson) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);", data)

connection.commit()