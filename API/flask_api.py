# importing required librareis
import play_scraper
import bs4
import requests
from flask import Flask, jsonify

# functoion to find all the links provide for an app and seperate the appIDs from there


def find_id(reque):
    reque.text
    soup = bs4.BeautifulSoup(reque.text, 'lxml')

    id_list = []  # List to store the IDs of apps

    # loop which find all the links with given class which contains the ID
    for tag in soup.find_all('a', {"class": 'poRVub'}):
        url_link = tag.get("href")  # url_link stores the link we found
        # we find the index of = as afterr  '=' we have the ID and store
        index = url_link.find("=")
        # taking a subStrig of url_link which contains ID and store it in 'app_id'
        app_id = url_link[index+1:]
        id_list.append(app_id)  # addind app_ids to the list of ids
    return id_list  # return the list of IDs


# function to find the top 5 apps apps and there ids
def top_apps(links=[]):  # links here has all the ids we found using the above function which is "find_id"
    top_app_list = []  # top_app_list is the final list which will store the ids of top 5 apps
    app_counter = 0   # used to iterate through the links[]
    review_list = []  # will store the total number of reviews of different apps
    size = len(links)  # finding total number of ids in links[]
    top_app_counter = 1  # app counter to make sure we take atmost 5 apps only

    # while loop here finds and stores reviews of all the apps
    while(app_counter < size):
        # we used the play_scraper library to find the
        app_data = play_scraper.details(links[app_counter])
        f = float(app_data['score'])
        if(f > 4):
            review_list.append(app_data['reviews'])
            app_counter += 1
        else:
            del links[app_counter]
            size = size-1

    # while loop below find the top 5 apps with mmost revies and stores their ids in top_app_list
    while (top_app_counter <= 5):
        maxi = max(review_list)
        index_list = (review_list.index(maxi))
        top_app_list.append(links[index_list])
        del links[index_list]
        del review_list[index_list]
        top_app_counter += 1

    # passing the top_app_list to another function which actuall returns the details of the apps
    final_app2 = app_details(top_app_list)

    return final_app2  # return app details

# function to find the details of the apps


def app_details(top_list=[]):
    app_no = 0
    # declaring multilple list to store the different elements of apps
    #name_list, icon_list, installs_list, score_list, category_list, reviews_list, screenshots_list, description_list = ([] for i in range(8))
    name_list, icon_list, installs_list, score_list, url_list = (
        [] for i in range(5))
    # store all thr data in lists
    while (app_no < 5):
        app_data = play_scraper.details(top_list[app_no])
        name_list.append(app_data['title'])
        icon_list.append(app_data['icon'])
        installs_list.append(app_data['installs'])
        score_list.append(app_data['score'])
        url_list.append(app_data['url'])
        # screenshots_list.append(app_data['screenshots'])
        # description_list.append(app_data['description'])
        # category_list.append(app_data['category'])
        # reviews_list.append(app_data['reviews'])
        app_no += 1
    # zip all the lists data in an array of objects so later could be converted to json format
    #final_app = [{"name": na,"icon":ico ,"screenshots":scr , "installs": ins , "score" : sco , "category": cate , "reviews" : rev, "description":desc } for na ,ico ,scr ,ins ,sco ,cate ,rev ,desc in  zip(name_list,icon_list,screenshots_list,installs_list,score_list,category_list,reviews_list,description_list)]
    final_app = [{"name": na, "icon": ico, "installs": ins, "score": sco, "url": ur}
                 for na, ico, ins, sco, ur in zip(name_list, icon_list, installs_list, score_list, url_list)]
    return final_app  # returning the array


# main api code
app = Flask(__name__)
@app.route("/games")  # making different routes for diffrent categories
def games_app_data_scraper():
    # sending request to the site
    res = requests.get(
        'https://play.google.com/store/apps/collection/cluster?clp=ygIPCgtHQU1FX0FDVElPThAB:S:ANO1ljJj-bc&gsr=ChLKAg8KC0dBTUVfQUNUSU9OEAE%3D:S:ANO1ljJnjlc&hl=en')
    # passing page to find_id function to find all the ids
    app_id_list = find_id(res)
    # passing the ids we get to find app reqired app details
    final_list = top_apps(app_id_list)
    # returning  the json array of with reqired information
    return jsonify(final_list)


@app.route("/photography")
def photo_app_data_scraper():

    res = requests.get(
        'https://play.google.com/store/apps/collection/cluster?clp=ygIPCgtQSE9UT0dSQVBIWRAB:S:ANO1ljJZKrM&gsr=ChLKAg8KC1BIT1RPR1JBUEhZEAE%3D:S:ANO1ljILES0&hl=en')
    res.text
    app_id_list = find_id(res)
    final_list = top_apps(app_id_list)
    return jsonify(final_list)

# remove multiline comments below to check other categories


@app.route("/education")
def edu_app_data_scraper():

    res = requests.get(
        'https://play.google.com/store/apps/collection/cluster?clp=ygINCglFRFVDQVRJT04QAQ%3D%3D:S:ANO1ljLqr7c&gsr=ChDKAg0KCUVEVUNBVElPThAB:S:ANO1ljLFFJI&hl=en')
    res.text
    app_id_list = find_id(res)
    final_list = top_apps(app_id_list)
    return jsonify(final_list)


@app.route("/social")
def social_app_data_scraper():
    res = requests.get(
        'https://play.google.com/store/apps/collection/cluster?clp=ygIKCgZTT0NJQUwQAQ%3D%3D:S:ANO1ljLh4yw&gsr=Cg3KAgoKBlNPQ0lBTBAB:S:ANO1ljLhTNQ&hl=en')
    res.text
    app_id_list = find_id(res)
    final_list = top_apps(app_id_list)
    return jsonify(final_list)


@app.route("/news")
def news_app_data_scraper():
    res = requests.get(
        'https://play.google.com/store/apps/category/NEWS_AND_MAGAZINES?hl=en')
    res.text
    app_id_list = find_id(res)
    final_list = top_apps(app_id_list)
    return jsonify(final_list)


@app.route("/music")
def music_app_data_scraper():
    res = requests.get(
        'https://play.google.com/store/apps/collection/cluster?clp=ygITCg9NVVNJQ19BTkRfQVVESU8QAQ%3D%3D:S:ANO1ljI3B5M&gsr=ChbKAhMKD01VU0lDX0FORF9BVURJTxAB:S:ANO1ljLdfEQ&hl=en')
    res.text
    app_id_list = find_id(res)
    final_list = top_apps(app_id_list)
    return jsonify(final_list)


@app.route("/entertainment")
def entertain_app_data_scraper():
    res = requests.get(
        'https://play.google.com/store/apps/collection/cluster?clp=ygIRCg1FTlRFUlRBSU5NRU5UEAE%3D:S:ANO1ljJSgK8&gsr=ChTKAhEKDUVOVEVSVEFJTk1FTlQQAQ%3D%3D:S:ANO1ljJF_js&hl=en')
    res.text
    app_id_list = find_id(res)
    final_list = top_apps(app_id_list)
    return jsonify(final_list)


@app.route("/productivity")
def product_app_data_scraper():
    res = requests.get(
        'https://play.google.com/store/apps/collection/cluster?clp=ygIQCgxQUk9EVUNUSVZJVFkQAQ%3D%3D:S:ANO1ljKF20U&gsr=ChPKAhAKDFBST0RVQ1RJVklUWRAB:S:ANO1ljLOtUE&hl=en')
    res.text
    app_id_list = find_id(res)
    final_list = top_apps(app_id_list)
    return jsonify(final_list)


@app.route("/communication")
def comm_app_data_scraper():
    res = requests.get(
        'https://play.google.com/store/apps/collection/cluster?clp=ygIRCg1DT01NVU5JQ0FUSU9OEAE%3D:S:ANO1ljJIF4Y&gsr=ChTKAhEKDUNPTU1VTklDQVRJT04QAQ%3D%3D:S:ANO1ljKuiog&hl=en')
    res.text
    app_id_list = find_id(res)
    final_list = top_apps(app_id_list)
    return jsonify(final_list)


@app.route("/")
def list_of_categories():
    return "add /games to url to find game app details \n similarly do for other categories"
