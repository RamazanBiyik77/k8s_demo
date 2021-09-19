FROM python:3.7.1

ENV FLASK_APP hello-world.py

RUN mkdir /app
WORKDIR /app

COPY app/hello-world/src/requirements.txt requirements.txt
RUN pip install -r requirements.txt

ADD app/hello-world/src /app

EXPOSE 5000

CMD flask run --host=0.0.0.0