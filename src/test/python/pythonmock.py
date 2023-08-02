from fastapi import FastAPI
from pydantic import BaseModel
import os

app = FastAPI()


@app.get("/getpaperinfo")
def getpaperinfo(paperId:str):
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
            "subjectRecommends": [ #수정함
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


class PythonQuestionDTO(BaseModel):
    paperId: str
    query: str
    history: list
@app.post("/question")
async def process_question(data: PythonQuestionDTO):
    print(data)
    return {"answer": "bibi"}

@app.get("/process")
def process_query(query: str):
    ret = {
        "answer": "reinforcement learning is a learning paradigm for solving sequential decision-making problems. It works by learning from trial-and-error interactions with the environment over its lifetime, and can be enhanced by incorporating causal relationships into the learning process.",
        "papers" : [
          {"paperId": "1706.03762",
          "year": "2020",
          "title": "Attention is All you",
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
# ---------------new -----------------------
class Paper:
    def __init__(this, paperId, year, title, authors, url, summary):
      this.papeId = paperId
      this.year =  year
      this.title = title
      this.summary = summary
      this.authors = authors
      this.url = url
    def toJson(this):
        ret = {}
        ret["paperId"] = this.papeId
        ret["title"] = this.title
        ret["summary"] = this.summary
        ret["year"] = this.year
        ret["authors"] = this.authors
        ret["url"] = this.url
        return ret
p1 = Paper(**{"paperId": "2204.13154", #str
          "year": 2022, #int
          "title": "Attention Mechanism in Neural Networks: Where it Comes and Where it Goes", #st
          "authors": ["Derya Soydaner"], #list<str>
          "url" : "https://arxiv.org/abs/2204.13154", #str
          "summary": "A long time ago in the machine learning literature, the idea of incorporating a mechanism inspired by the human visual system into neural networks was introduced. This idea is named the attention mechanism, and it has gone through a long development period. Today, many works have been devoted to this idea in a variety of tasks. Remarkable performance has recently been demonstrated. The goal of this paper is to provide an overview from the early work on searching for ways to implement attention idea with neural networks until the recent trends. This review emphasizes the important milestones during this progress regarding different tasks. By this way, this study aims to provide a road map for researchers to explore the current development and get inspired for novel approaches beyond the attention." #str
          })
p2 = Paper("1909.13474", 2019, "Spatio-Temporal FAST 3D Convolutions for Human Action Recognition",
           ["Alexandros Stergiou", "Ronald Poppe"], "https://arxiv.org/abs/1909.13474", "Effective processing of video input is essential for the recognition of temporally varying events such as human actions. Motivated by the often distinctive temporal characteristics of actions in either horizontal or vertical direction, we introduce a novel convolution block for CNN architectures with video input. Our proposed Fractioned Adjacent Spatial and Temporal (FAST) 3D convolutions are a natural decomposition of a regular 3D convolution. Each convolution block consist of three sequential convolution operations: a 2D spatial convolution followed by spatio-temporal convolutions in the horizontal and vertical direction, respectively. Additionally, we introduce a FAST variant that treats horizontal and vertical motion in parallel. Experiments on benchmark action recognition datasets UCF-101 and HMDB-51 with ResNet architectures demonstrate consistent increased performance of FAST 3D convolution blocks over traditional 3D convolutions. The lower validation loss indicates better generalization, especially for deeper networks. We also evaluate the performance of CNN architectures with similar memory requirements, based either on Two-stream networks or with 3D convolution blocks. DenseNet-121 with FAST 3D convolutions was shown to perform best, giving further evidence of the merits of the decoupled spatio-temporal convolutions." )
p3 = Paper("1903.08131", 2019, "Kernel-based Translations of Convolutional Networks", ["Corinne Jones, Vincent Roulet, Zaid Harchaoui"],"https://arxiv.org/abs/1903.08131","Convolutional Neural Networks, as most artificial neural networks, are commonly viewed as methods different in essence from kernel-based methods. We provide a systematic translation of Convolutional Neural Networks (ConvNets) into their kernel-based counterparts, Convolutional Kernel Networks (CKNs), and demonstrate that this perception is unfounded both formally and empirically. We show that, given a Convolutional Neural Network, we can design a corresponding Convolutional Kernel Network, easily trainable using a new stochastic gradient algorithm based on an accurate gradient computation, that performs on par with its Convolutional Neural Network counterpart. We present experimental results supporting our claims on landmark ConvNet architectures comparing each ConvNet to its CKN counterpart over several parameter settings.")
p4 = Paper("2212.09507", 2022, "VC dimensions of group convolutional neural networks", ["Philipp Christian Petersen", "Anna Sepliarskaia"], "https://arxiv.org/abs/2212.09507", "We study the generalization capacity of group convolutional neural networks. We identify precise estimates for the VC dimensions of simple sets of group convolutional neural networks. In particular, we find that for infinite groups and appropriately chosen convolutional kernels, already two-parameter families of convolutional neural networks have an infinite VC dimension, despite being invariant to the action of an infinite group.")
p5 = Paper("2010.01369", 2010, "Computational Separation Between Convolutional and Fully-Connected Networks", ["Eran Malach", "Shai Shalev-Shwartz"], "https://arxiv.org/abs/2010.01369", "Convolutional neural networks (CNN) exhibit unmatched performance in a multitude of computer vision tasks. However, the advantage of using convolutional networks over fully-connected networks is not understood from a theoretical perspective. In this work, we show how convolutional networks can leverage locality in the data, and thus achieve a computational advantage over fully-connected networks. Specifically, we show a class of problems that can be efficiently solved using convolutional networks trained with gradient-descent, but at the same time is hard to learn using a polynomial-size fully-connected network.")
p6 = Paper("2111.00977", 2011, "Fast Convolution based on Winograd Minimum Filtering: Introduction and Development", ["Gan Tong", "Libo Huang"], "https://arxiv.org/abs/2111.00977","Convolutional Neural Network (CNN) has been widely used in various fields and played an important role. Convolution operators are the fundamental component of convolutional neural networks, and it is also the most time-consuming part of network training and inference. In recent years, researchers have proposed several fast convolution algorithms including FFT and Winograd. Among them, Winograd convolution significantly reduces the multiplication operations in convolution, and it also takes up less memory space than FFT convolution. Therefore, Winograd convolution has quickly become the first choice for fast convolution implementation within a few years. At present, there is no systematic summary of the convolution algorithm. This article aims to fill this gap and provide detailed references for follow-up researchers. This article summarizes the development of Winograd convolution from the three aspects of algorithm expansion, algorithm optimization, implementation, and application, and finally makes a simple outlook on the possible future directions.")
p7 = Paper("1706.03762", 2017, "Attention is all you need", list("Ashish Vaswani, Noam Shazeer, Niki Parmar, Jakob Uszkoreit, Llion Jones, Aidan N. Gomez, Lukasz Kaiser, Illia Polosukhin".split(", ")), "https://arxiv.org/abs/1706.03762", "The dominant sequence transduction models are based on complex recurrent or convolutional neural networks in an encoder-decoder configuration. The best performing models also connect the encoder and decoder through an attention mechanism. We propose a new simple network architecture, the Transformer, based solely on attention mechanisms, dispensing with recurrence and convolutions entirely. Experiments on two machine translation tasks show these models to be superior in quality while being more parallelizable and requiring significantly less time to train. Our model achieves 28.4 BLEU on the WMT 2014 English-to-German translation task, improving over the existing best results, including ensembles by over 2 BLEU. On the WMT 2014 English-to-French translation task, our model establishes a new single-model state-of-the-art BLEU score of 41.8 after training for 3.5 days on eight GPUs, a small fraction of the training costs of the best models from the literature. We show that the Transformer generalizes well to other tasks by applying it successfully to English constituency parsing both with large and limited training data.")
p8 = Paper("2307.00865", 2023, "A Survey on Graph Classification and Link Prediction based on GNN", ["Xingyu Liu", "Juan Chen", "Quan Wen"], "https://arxiv.org/abs/2307.00865", "Traditional convolutional neural networks are limited to handling Euclidean space data, overlooking the vast realm of real-life scenarios represented as graph data, including transportation networks, social networks, and reference networks. The pivotal step in transferring convolutional neural networks to graph data analysis and processing lies in the construction of graph convolutional operators and graph pooling operators. This comprehensive review article delves into the world of graph convolutional neural networks. Firstly, it elaborates on the fundamentals of graph convolutional neural networks. Subsequently, it elucidates the graph neural network models based on attention mechanisms and autoencoders, summarizing their application in node classification, graph classification, and link prediction along with the associated datasets.")
P = [p1, p2, p3, p4, p5, p6, p7, p8]
metainfo_db = {p.papeId: p for p in P}

@app.get("/db/paper/metainfo")
def paper_meta_info(paperId: str):
    try:
        c = metainfo_db[paperId].toJson()
        return c
    except Exception as e:
        print(e)
        return None
