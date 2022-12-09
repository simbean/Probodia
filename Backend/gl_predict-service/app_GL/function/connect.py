from sqlalchemy import create_engine
import pymysql
pymysql.install_as_MySQLdb()
import json
import pandas as pd





def get_engine():
    try:
        with open("/run/secrets/db") as f: 
            #with open("/home/ubuntu/DBinform.json") as f:
            DB = json.load(f)
        engine = create_engine("mysql+mysqldb://" + DB['USER'] + ":" + DB['PASSWORD'] + "@"
                                                + DB['HOST'] + ":" + str(DB['PORT']) + "/" + DB["DBNAME"], encoding='utf-8')
        print("Connect engine")
    except:
        raise Exception('not connect DB')
    return engine


def get_DataFrame(table : str, engine):
    connection = engine.connect()
    result = pd.read_sql(f'SELECT * FROM {table}', connection)
    connection.close()
    return result
