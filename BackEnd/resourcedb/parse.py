import sqlite3 as lite
import sys, csv

uid = 0
tmp = 0

data=[]

connection = lite.connect('housing.db')
cursor = connection.cursor()
cursor.execute("CREATE TABLE Houses(id INT, place_name TEXT, place_id INT, year INT, period INT, index_nsa FLOAT, index_sa FLOAT)")

creader = csv.reader(open('housing.csv', 'rt'), delimiter=',')
for t in creader:
	if(tmp == 0):
		tmp += 1
		continue
	data.append([uid] + t)
	if(uid < 20):
		print(data[uid])
	uid += 1


cursor.executemany("INSERT INTO Houses (id, place_name, place_id, year, period, index_nsa, index_sa) VALUES (?, ?, ?, ?, ?, ?, ?);", data)
connection.commit()