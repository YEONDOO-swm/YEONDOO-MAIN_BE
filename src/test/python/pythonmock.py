from fastapi import FastAPI
from pydantic import BaseModel
import os

app = FastAPI()


class PythonQuestionDTO(BaseModel):
    paperid: str
    history: list
    query: str
@app.get("/getpaperinfo")
def getpaperinfo(paperid:str):
    return {"summary" : "A long time ago in the machine learning literature, the idea of incorporating a mechanism inspired by the human visual system into neural networks was introduced. This idea is named the attention mechanism, and it has gone through a long development period. Today, many works have been devoted to this idea in a variety of tasks. Remarkable performance has recently been demonstrated. The goal of this paper is to provide an overview from the early work on searching for ways to implement attention idea with neural networks until the recent trends. This review emphasizes the important milestones during this progress regarding different tasks. By this way, this study aims to provide a road map for researchers to explore the current development and get inspired for novel approaches beyond the attention.\n",
            "insights" : [
                "Ashish Vaswani",
                "Noam Shazeer",
                "Niki Parmar"
            ],
            "questions" : [
              "gpt가 만든 질문 1",
              "gpt가 만든 질문 2",
              "gpt가 만든 질문 3"
            ],
            "subjectrecommends": [
              "향후 연구 주제 추천 1",
              "향후 연구 주제 추천 2",
              "향후 연구 주제 추천 3"
            ],
            "references": [
              "ref1",
              "ref2",
              "ref3"
            ]
    }


@app.post("/question")
async def process_question(data: PythonQuestionDTO):
    return {"answer": "bibi"}

@app.get("/process")
def process_query(query: str):
    ret = {
        "answer": "reinforcement learning is a learning paradigm for solving sequential decision-making problems. It works by learning from trial-and-error interactions with the environment over its lifetime, and can be enhanced by incorporating causal relationships into the learning process.",
        "papers" : [
          {"paperId": "1706.03762",
          "year": "2020",
          "title": "Some Insights into Lifelong Reinforcement Learning Systems",
          "authors": ["Changjian Li"],
          "summary": "~~~",
          "url" : "http~~~"
          },
        {"paperId": "2204.13154",
          "year": "2020",
          "title": "Insights into Lifelong Reinforcement Learning Systems",
          "authors": ["Changjian Li", "hihi"],
          "summary": "~~~",
          "url" : "http~~~"
          }
        ]
    }

    return ret #결과값 딕셔너리 형태로 반환
