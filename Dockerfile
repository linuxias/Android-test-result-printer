FROM python:3.7-slim-buster

RUN apt-get -y update

COPY ResultPrinter.py /ResultPrinter.py
COPY requirements.txt /requirements.txt

RUN python -m pip install --upgrade pip
RUN pip install -r /requirements.txt

ENTRYPOINT ["python3.7", "ResultPrinter.py"]
