package com.eyeslessdev.needmypuppyapi.service;

import com.eyeslessdev.needmypuppyapi.entity.SearchCriteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Service
class BreedSelectorService {

    //inner parameters
    private int time;
    private int exp;
    private int age;
    private int athlet;
    private int cynologist;
    private int walk;
    private int family;
    private int grummer;

    //outerparrameters
    private int obidience;
    private int guard;
    private int agressive;
    private int active;
    private int size;
    private int care;

    // main logic for convert inner parameters to outer parameters
    List<SearchCriteria> getCriteriaListFromSelector(Map<String, Integer> selectorparams) {

        List<SearchCriteria> outcomecriteria = new ArrayList<>();

        //extract values from map
        for (Map.Entry<String, Integer> item : selectorparams.entrySet()) {

            switch (item.getKey()) {
                case "time":
                    setTime(item.getValue());
                    break;
                case "exp":
                    setExp(item.getValue());
                    break;
                case "age":
                    setAge(item.getValue());
                    break;
                case "athlet":
                    setAthlet(item.getValue());
                    break;
                case "cyno":
                    setCynologist(item.getValue());
                    break;
                case "walk":
                    setWalk(item.getValue());
                    break;
                case "fam":
                    setFamily(item.getValue());
                    break;
                case "grum":
                    setGrummer(item.getValue());
                    break;
            }
        }

        //послушка
        setObidience(0);
        setObidience(max(getObidience(), max(4 - getTime(), 5 - finalyactive()))); //чем меньше времени тем выше должно быть послушание породы, чем меньше активность тем выше послушание породы

        if (getExp() < 2 && getCynologist() < 4) {setObidience(min(getObidience(), 3));} //если нет опыта и нет кинолога, то минимальная послушка 3

        if (getTime() > 1 && getCynologist() == 4) {setObidience(min(getObidience(), 2));} //если времени более часа в день и есть отличный доступ к кинологу, повысить минимально допустимое послушание до 2, даже если ранее выставлено больше

        if (getExp() > 3) {setObidience(min(getObidience(), 1));} //если опыт ЭКСПЕРТ то уменьшить минимально допустимое послушание до 1, даже если ранее выставлено больше

        if (getAge() < 2) {setObidience(max(getObidience(), 3));} //если возраст менее 16 лет то увеличить минимально допустимое послушание до 3, даже если ранее выставлено меньше

        outcomecriteria.add(new SearchCriteria("obidience", ">", getObidience()));

        //охранные качества - в текущей версии параметр фактичесски не используется
        setGuard(5);
        if (getWalk() == 5) {setGuard(max(getGuard(), 5));}//если собака содержится на своем участке, то то максимально охранные качества 5

        outcomecriteria.add(new SearchCriteria("guard", "<", getGuard()));


        //агрессия
        setAgressive(5);
        setAgressive(min(getAgressive(), max(getExp() + 1, getTime() + 2))); //минимальное значение из прямой зависимости от опыта и времени,

        if (getTime() > 1 && (getCynologist() == 4 || getExp() > 3)) {setAgressive(max(getAgressive(), getTime() + 2));} //если времени  2-3 часа в день и есть отличный доступ к кинологу или пользователь ЭКСПЕРТ, повысить максимально допустимую агрессию до 4, даже если ранее выставлено больше

        if (getWalk() == 5 && getExp() > 1) {setAgressive(max(getAgressive(), (getExp() + 2)));}//если собака содержится на своем участке, то допускается повышенная агрессия, кореллирующая с опытом

        if (getAge() < 2 || (getAge() == 5 && getFamily() != 3 && getWalk() != 5)) {setAgressive(min(getAgressive(), 2));} //возраст менее 16лет или более 60 лет при отсутствии более активных членов семьи, то уменьшить максимально допустимую агрессивность до 2, даже если ранее выставлено больше

        if (finalyactive() < 3) {setAgressive(min(getAgressive(), 2));} //если уровень физической активности не удовлетворительный то уменьшить максимально допустимую агрессивность до 2, даже если ранее выставлено больше

        outcomecriteria.add(new SearchCriteria("agressive", "<", getAgressive()));

        //активность
        setActive(5);
        setActive(min(getActive(), max(finalyactive(), max(getWalk() + 1, getTime() + 2)))); //чем больше времени или активности, тем более активная порода допускается, также если хорошие условия выгула

        if (getTime() < 2 && (getExp() < 3 || getCynologist() < 4)) {setActive(min(getActive(), 4));} //если нет опыта и нет кинолога или времени, то минимальная активность 4

        if (getAge() > 3) {setActive(min(getActive(), finalyactive()));} //если возраст больше 40 то активность собаки прямо равна активность (finalyactive)
        if (finalyactive() < 3) {setActive(min(getActive(), 3));} //если уровень физической активности не удовлетворительный то уменьшить максимально допустимую активность до 3, даже если ранее выставлено больше

        outcomecriteria.add(new SearchCriteria("active", "<", getActive()));

        //размер
        setSize(5);
        if (getAge() < 2 || getAge() == 5 || finalyactive() < 2) {setSize(min(getSize(), 3));} //если возраст менее 16 или активность менее нормальной (для старшего возраста менее хорошей), то максимальный размер не более 3

        if (getCynologist() < 3 || getExp() < 2) {setSize(min(getSize(), 3));} //если нет кинолога или опыта то размер не более 3

        if (getCynologist() > 2 || getExp() > 1) {setSize(min(getSize(), max(getTime() + 2, getWalk() + 1)));} //если есть опыт или кинолог то прямая зависимость от времени

        if (getWalk() > 3 && getExp() > 1) {setSize(max(getSize(), getExp() + 2));} //если есть собственный участок и опыт не "нет", то размер = опыт +2

        if (getExp() < 4) {setSize(min(getSize(), getWalk() + 2));} //если нет места для выгула то размер уменьшается кроме экспертных пользователей

        outcomecriteria.add(new SearchCriteria("size", "<", getSize()));

        //уход
        setCare(5);
        if (getAge() < 2 || (getAge() == 5 && getFamily() != 3)) {setCare(min(getCare(), 2));} //возраст менее 16лет или более 60 лет при отсутствии более активных членов семьи, то уменьшить заботу до 2

        setCare(min(getCare(), max(1, max(getGrummer(), getTime())))); //сложность в уходе напрямую зависит от наличия времени или доступа к груммеру

        outcomecriteria.add(new SearchCriteria("care", "<", getCare()));

        return outcomecriteria;
    }

    //корректируем показатель активности от возраста или от менее активных членов семьи
    private int finalyactive (){

        int activwithage = getAthlet()+1;

        if (getAge() == 5){activwithage --;}

        if (getFamily() ==4) {activwithage --;}

        return activwithage;
    }

    List<SearchCriteria> getCriteriaListFromExterier(Map<String, String> selectorparams){

        List<SearchCriteria> outcomecriteria = new ArrayList<>();

        for (Map.Entry<String, String> item : selectorparams.entrySet()) {

            //its very stupid but in db (and also in Breed.class) parameter "hairsize" turn to "hair" and "rare" turn to "imageidbig" (surprise!!!)
            switch (item.getKey()) {
                case "hairsize":
                    if (!item.getValue().equals("any")){
                    outcomecriteria.add(new SearchCriteria("hair", ":", item.getValue()));}
                    break;
                case "blackorwhite":
                    if (!item.getValue().equals("any")){
                        outcomecriteria.add(new SearchCriteria(item.getKey(), ":", item.getValue()));}
                    break;
                case "rare":
                    outcomecriteria.add(new SearchCriteria("imageresourceidbig", ":", item.getValue()));
                    break;

    }
        }
        return outcomecriteria;
    }


    private int getTime() {
        return time;
    }

    private void setTime(int time) {
        this.time = time;
    }

    private int getExp() {
        return exp;
    }

    private void setExp(int exp) {
        this.exp = exp;
    }

    private int getAge() {
        return age;
    }

    private void setAge(int age) {
        this.age = age;
    }

    private int getAthlet() {
        return athlet;
    }

    private void setAthlet(int athlet) {
        this.athlet = athlet;
    }

    private int getCynologist() {
        return cynologist;
    }

    private void setCynologist(int cynologist) {
        this.cynologist = cynologist;
    }

    private int getWalk() {
        return walk;
    }

    private void setWalk(int walk) {
        this.walk = walk;
    }

    private int getFamily() {
        return family;
    }

    private void setFamily(int family) {
        this.family = family;
    }

    private int getGrummer() {
        return grummer;
    }

    private void setGrummer(int grummer) {
        this.grummer = grummer;
    }

    private int getObidience() {
        return obidience;
    }

    private void setObidience(int obidience) {
        this.obidience = obidience;
    }

    private int getGuard() {
        return guard;
    }

    private void setGuard(int guard) {
        this.guard = guard;
    }

    private int getAgressive() {
        return agressive;
    }

    private void setAgressive(int agressive) {
        this.agressive = agressive;
    }

    private int getActive() {
        return active;
    }

    private void setActive(int active) {
        this.active = active;
    }

    private int getSize() {
        return size;
    }

    private void setSize(int size) {
        this.size = size;
    }

    private int getCare() {
        return care;
    }

    private void setCare(int care) {
        this.care = care;
    }

}

//создаем 12 корзин для основных свойств выбора собаки
//послушание 1- породы не способные к дрессировке, 2 -хаски, 5 миалинуа
//охрана 1-хаски 5-малинуа
//агрессивность 1-хаски 5-САО
//активность 1-флегматичные собаки 5-джекрассел
//выносливость 1-утомляемые собаки 4-хаски 5-риджбэк
//размер 1-чихуа 2-джекрассел 3-хаски, лабр 4 - малинуа 5-САО
//уход 1-не нуждается 5-специфичная длинная шерсть или стандарты грумминга
//охота 1 - ПОДХОДИТ для охоты 0 НЕ подходит для охоты
//гипоалергенная алергенная шерсть 1 - требуетя 0 не требуется
//чистый черный или белый окрас
//добавить (1) или не добавлять (0) редкие породы
