FROM python:3.8


ADD /app /app
WORKDIR /app
RUN apt-get update
RUN pip install --upgrade pip
RUN pip install --no-cache-dir -r requirements.txt
RUN apt-get -y install libgl1-mesa-glx



CMD uwsgi uwsgi.ini
