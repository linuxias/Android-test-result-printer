FROM python:3.7-slim-buster

RUN apt-get -y update

ADD ResultPrinter.py /home/ResultPrinter.py
ADD requirements.txt /home/requirements.txt
ADD TodoList /home/TodoList

RUN python -m pip install --upgrade pip
RUN pip install -r /home/requirements.txt

CMD ["python3.7", "/home/ResultPrinter.py"]
