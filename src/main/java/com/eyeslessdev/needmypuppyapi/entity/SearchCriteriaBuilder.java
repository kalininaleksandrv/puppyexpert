package com.eyeslessdev.needmypuppyapi.entity;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Component
public class SearchCriteriaBuilder {

    //outerparrameters
    private int obidience;
    private int guard;
    private int agressive;
    private int active;
    private int size;
    private int care;


    public Specification<SearchCriteria> buildListOfCriteria (BreedRequest breedRequest){

        List<SearchCriteria> outcomecriteria = new ArrayList<>();

        //послушка
        setObidience(0);
        setObidience(max(getObidience(), max(4 - breedRequest.getThetime(), 5 - finalyactive(breedRequest)))); //чем меньше времени тем выше должно быть послушание породы, чем меньше активность тем выше послушание породы

        if (breedRequest.getExp() < 2 && breedRequest.getCynologist() < 4) {setObidience(min(getObidience(), 3));} //если нет опыта и нет кинолога, то минимальная послушка 3

        if (breedRequest.getThetime() > 1 && breedRequest.getCynologist() == 4) {setObidience(min(getObidience(), 2));} //если времени более часа в день и есть отличный доступ к кинологу, повысить минимально допустимое послушание до 2, даже если ранее выставлено больше

        if (breedRequest.getExp() > 3) {setObidience(min(getObidience(), 1));} //если опыт ЭКСПЕРТ то уменьшить минимально допустимое послушание до 1, даже если ранее выставлено больше

        if (breedRequest.getAge() < 2) {setObidience(max(getObidience(), 3));} //если возраст менее 16 лет то увеличить минимально допустимое послушание до 3, даже если ранее выставлено меньше

        outcomecriteria.add(new SearchCriteria("obidience", ">", getObidience()));

        //охранные качества - в текущей версии параметр фактичесски не используется
        setGuard(5);
        if (breedRequest.getWalk() == 5) {setGuard(max(getGuard(), 5));}//если собака содержится на своем участке, то то максимально охранные качества 5

        outcomecriteria.add(new SearchCriteria("guard", "<", getGuard()));

        //активность
        setActive(5);
        setActive(min(getActive(), max(finalyactive(breedRequest), max(breedRequest.getWalk() + 1, breedRequest.getThetime() + 2)))); //чем больше времени или активности, тем более активная порода допускается, также если хорошие условия выгула

        if (breedRequest.getThetime() < 2 && (breedRequest.getExp() < 3 || breedRequest.getCynologist() < 4)) {setActive(min(getActive(), 4));} //если нет опыта и нет кинолога или времени, то минимальная активность 4

        if (breedRequest.getAge() > 3) {setActive(min(getActive(), finalyactive(breedRequest)));} //если возраст больше 40 то активность собаки прямо равна активность (finalyactive)
        if (finalyactive(breedRequest) < 3) {setActive(min(getActive(), 3));} //если уровень физической активности не удовлетворительный то уменьшить максимально допустимую активность до 3, даже если ранее выставлено больше

        outcomecriteria.add(new SearchCriteria("active", "<", getActive()));

        //размер
        setSize(5);
        if (breedRequest.getAge() < 2 || breedRequest.getAge() == 5 || finalyactive(breedRequest) < 2) {setSize(min(getSize(), 3));} //если возраст менее 16 или активность менее нормальной (для старшего возраста менее хорошей), то максимальный размер не более 3

        if (breedRequest.getCynologist() < 3 || breedRequest.getExp() < 2) {setSize(min(getSize(), 3));} //если нет кинолога или опыта то размер не более 3

        if (breedRequest.getCynologist() > 2 || breedRequest.getExp() > 1) {setSize(min(getSize(), max(breedRequest.getThetime() + 2, breedRequest.getWalk() + 1)));} //если есть опыт или кинолог то прямая зависимость от времени

        if (breedRequest.getWalk() > 3 && breedRequest.getExp() > 1) {setSize(max(getSize(), breedRequest.getExp() + 2));} //если есть собственный участок и опыт не "нет", то размер = опыт +2

        if (breedRequest.getExp() < 4) {setSize(min(getSize(), breedRequest.getWalk() + 2));} //если нет места для выгула то размер уменьшается кроме экспертных пользователей

        outcomecriteria.add(new SearchCriteria("size", "<", getSize()));

        //агрессия
        setAgressive(5);
        setAgressive(min(getAgressive(), max(breedRequest.getExp() + 1, breedRequest.getThetime() + 2))); //минимальное значение из прямой зависимости от опыта и времени,

        if (breedRequest.getThetime() > 1 && (breedRequest.getCynologist() == 4 || breedRequest.getExp() > 3)) {setAgressive(max(getAgressive(), breedRequest.getThetime() + 2));} //если времени  2-3 часа в день и есть отличный доступ к кинологу или пользователь ЭКСПЕРТ, повысить максимально допустимую агрессию до 4, даже если ранее выставлено больше

        if (breedRequest.getWalk() == 5 && breedRequest.getExp() > 1) {setAgressive(max(getAgressive(), (breedRequest.getExp() + 2)));}//если собака содержится на своем участке, то допускается повышенная агрессия, кореллирующая с опытом

        if (breedRequest.getAge() < 2 || (breedRequest.getAge() == 5 && breedRequest.getFamily() != 3 && breedRequest.getWalk() != 5)) {setAgressive(min(getAgressive(), 2));} //возраст менее 16лет или более 60 лет при отсутствии более активных членов семьи, то уменьшить максимально допустимую агрессивность до 2, даже если ранее выставлено больше

        if (finalyactive(breedRequest) < 3) {setAgressive(min(getAgressive(), 2));} //если уровень физической активности не удовлетворительный то уменьшить максимально допустимую агрессивность до 2, даже если ранее выставлено больше

        outcomecriteria.add(new SearchCriteria("agressive", "<", getAgressive()));


        //уход
        setCare(5);
        if (breedRequest.getAge() < 2 || (breedRequest.getAge() == 5 && breedRequest.getFamily() != 3)) {setCare(min(getCare(), 2));} //возраст менее 16лет или более 60 лет при отсутствии более активных членов семьи, то уменьшить заботу до 2

        setCare(min(getCare(), max(1, max(breedRequest.getGrummer(), breedRequest.getThetime())))); //сложность в уходе напрямую зависит от наличия времени или доступа к груммеру

        outcomecriteria.add(new SearchCriteria("care", "<", getCare()));

        //критерии экстерьера
        //if hairsize not equal "any", we're add hairsinze parameter to our request
        if (!breedRequest.getHairsize().equals("any")){
            outcomecriteria.add(new SearchCriteria("hair", ":", breedRequest.getHairsize()));}

        //if blackorwhite not equal "any", we're add blackorwhite parameter to our request
        if (!breedRequest.getBlackorwhite().equals("any")){
            outcomecriteria.add(new SearchCriteria("blackorwhite", ":", breedRequest.getBlackorwhite()));}

        //if rare equal "no", the user has NOT added a flag "add rare breeds" in his request,
        // (so he wishes not to add rare breeds to results) we add "rare=no" param to our search;
        // otherwise, we do not add this param, so there we'll be search with any value of "rare" field
        if(breedRequest.getRare().equals("no")){
            outcomecriteria.add(new SearchCriteria("imageresourceidbig", ":", breedRequest.getRare()));}

        BreedSpecificationBuilder bsb = new BreedSpecificationBuilder();

        for(SearchCriteria item : outcomecriteria){
            bsb = bsb.with(item);
        }


        return bsb.build();

    }

    //корректируем показатель активности от возраста или от менее активных членов семьи
    private int finalyactive (BreedRequest breedRequest){

        int activwithage = breedRequest.getAthlet()+2;

        if (breedRequest.getAge() == 5){activwithage --;}

        if (breedRequest.getFamily() ==4) {activwithage --;}

        return activwithage;
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
