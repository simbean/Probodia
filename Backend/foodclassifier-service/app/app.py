#-*- coding: utf-8 -*-
from flask import Flask, jsonify, request, render_template  # 서버 구현을 위한 Flask 객체 import
from flask_restx import Api, Resource, fields  # Api 구현을 위한 Api 객체 import
from flask_jwt_extended import JWTManager, jwt_required, get_jwt_identity, get_jwt
from function.connect import s3_connection
from function.predict import get_image, get_predictions
from function.logger import create_logger
import json
import pandas as pd
import ssl
import logging, logstash
app = Flask(__name__)  # Flask 객체 선언, 파라미터로 어플리케이션 패키지의 이름을 넣어줌.
jwt = JWTManager(app)
api = Api(app)  # Flask 객체에 Api 객체 등록

#with open('/root/ssl/inform.json') as f:
with open('/run/secrets/AWS_KEY') as f:
    data = json.load(f)


app.config['PROPAGATE_EXCEPTIONS'] = True
foodclassifier = api.namespace('predict', description = '음식 분류기')
#logger = create_logger('food-classification', logstash_server_url)
S3, Bucket = s3_connection(data)
df = pd.read_csv('static/inform/aidb.csv', encoding = 'cp949')
#logstash_server_url = data['LOGSTASH_SERVER']
app.config['JWT_SECRET_KEY'] = data['JWT_ACCESSKEY']


model = foodclassifier.model('new article', strict=True, model={
    'img_path': fields.String(title='food link', required=True),
})



@foodclassifier.route('', methods=["POST"])
class predict(Resource):
    @foodclassifier.expect(model, validate = True)
    @jwt_required()
    def post(self):
        current_user = get_jwt_identity()
        file = request.get_json()
        img_path = file['img_path']
        img = get_image(img_path = img_path, s3 = S3, bucket_name = Bucket)
        class_id = get_predictions(img, df)
        #logger.info(current_user)
        return jsonify({'class_id': class_id})


@app.route("/PrivacyPolicy")
def policy():
    return render_template("index.html")


if __name__ == "__main__":
    app.run(debug=True, host='0.0.0.0', port=5000)