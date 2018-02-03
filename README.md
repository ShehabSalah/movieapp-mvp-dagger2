# movieapp-mvp MVP + Dagger 2 (Dagger-Android)
### Summary
This sample is based on the [MVP sample](https://github.com/ShehabSalah/movieapp-mvp) and uses Dagger to externalize the creation of dependencies from the classes that use them.

Dependency injection is a concept valid for any programming language. The general concept behind dependency injection is called Inversion of Control. According to this concept a class should not configure its dependencies statically but should be configured from the outside.

A Java class has a dependency on another class, if it uses an instance of this class. We call this a _class dependency. For example, a class which accesses a logger service has a dependency on this service class.

Ideally Java classes should be as independent as possible from other Java classes. This increases the possibility of reusing these classes and to be able to test them independently from other classes.

If the Java class creates an instance of another class via the new operator, it cannot be used (and tested) independently from this class and this is called a hard dependency.

## Dagger 2 API

Dagger 2 exposes a number of special annotations:
- <code> @Module </code> for the classes whose methods provide dependencies 
- <code> @Provides </code> for the methods within <code> @Module </code> classes
- <code> @Inject </code> to request a dependency (a constructor, a field, or a method)
- <code> @Component </code> is a bridge interface between modules and injection

These are the most important annotations you need to know about to get started with dependency injection using Dagger 2.

## Dagger 2 Workflow
To implement Dagger 2 correctly, you have to follow these steps:
- Identify the dependent objects and its dependencies.
- Create a class with the <code> @Module </code> annotation, using the <code> @Provides </code> annotation for every method that returns a dependency.
- Request dependencies in your dependent objects using the <code> @Inject </code> annotation.
- Create an interface using the <code> @Component </code> annotation and add the classes with the <code> @Module </code> annotation created in the second step.
- Create an object of the <code> @Component </code> interface to instantiate the dependent object with its dependencies.

## Dagger Android
We will briefly look at two annotations : <code> @Binds </code> and <code> @ContributesAndroidInjector </code>.

#### @Binds
This annotation provides a replacement of <code> @Provides </code> methods which simply return the injected parameter. Let’s take an example,

We have a MoviesPresenter which implements MoviesContract.Presenter. Without <code> @Binds </code>, the provider method for it will be something as follows :

```java
@Module
public class MovieModule {
    
    @Provides
    public MoviesContract.Presenter provideMoviesPresenter(MoviesPresenter moviesPresenter) {
        return moviesPresenter;
    }
    
}
```
In the above case, we can instead use <code> @Binds </code> annotation and make the above method <code> abstract </code>:

```java
@Module
public abstract class MovieModule {
    
    @Binds
    public abstract MoviesContract.Presenter provideMoviesPresenter(MoviesPresenter moviesPresenter);
    
}
```
Of course, we will also need to mark our Module as abstract in this case, which is more efficient than a concrete one and thus makes <code> @Binds </code> more efficient.

#### What makes our abstract Module more performing?
<code> @Provides </code> methods are instance methods and they need an instance of our module in order to be invoked. If our Module is abstract and contains <code> @Binds </code> methods, dagger will not instantiate our module and instead directly use the Provider of our injected parameter (MoviesPresenter in the above case).

#### What if your module contains both <code> @Provides </code> and <code> @Binds </code> methods?
In case, your Module has both <code> @Provides </code> and <code> @Binds </code> methods, you have two options :
- Simplest would be to mark your <code> @Provides </code> instance methods as <code> static </code>.
- If it is necessary to keep them as instance methods, then you can split your module into two and extract out all the <code> @Binds </code> methods into an abstract Module.

```java
@Module
public abstract class MovieDetailsModule {
    
    @Provides
    static Movie provideMovie(DetailsActivity activity) {
        return activity.getIntent().getParcelableExtra(Constants.MOVIE_EXTRA);
    }
    
    @Binds
    abstract DetailsContract.presenter detailsPresenter(DetailsPresenter presenter);

}
```
#### @ContributesAndroidInjector

Dagger Android introduced this annotation which can reduce the <code> Component </code>, <code> Binds </code>, <code> IntoSet </code>, <code> Subcomponent </code>, <code> ActivityKey </code>, <code> FragmentKey </code> etc. boilerplate for you.

If you have a simple module like the following, you can then let dagger handle the rest.

```java
@Module
public abstract class MovieModule {
    
    @Binds
    public abstract MoviesContract.Presenter provideMoviesPresenter(MoviesPresenter moviesPresenter);
    
}
```
All you need to do is write the following snippet inside the <code> Module </code> of the component, which is going to be the super-component of the generated <code> DetailsComponent </code>. Example, If you have an <code> AppComponent </code> and you want dagger to generate a <code> DetailsSubcomponent </code> for your <code> DetailsActivity </code>, you will write the following snippet inside your <code> ActivityBindingModule </code>.

```java
@Module
public abstract class ActivityBindingModule {
    
    @ContributesAndroidInjector(modules = {MovieDetailsModule.class})
    abstract DetailsActivity detailsActivity();
    
}
```

We want <code> Dagger.Android </code> to create a Subcomponent which has a parent <code> Component </code> of whichever module <code> ActivityBindingModule </code> is on, in our case that will be <code> AppComponent </code>.

The beautiful part about this setup is that you never need to tell <code> AppComponent </code> that it is going to have all these subcomponents nor do you need to tell these subcomponents that <code> AppComponent </code> exists.

It’s a good practice to extract out a separate <code> ActivityBindingModule </code> for all such bindings and include it in the modules list of your <code> AppComponent </code> as follows :

```java
@Singleton
@Component(modules = {
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<ApplicationClass> {
   
}
```

<code> AndroidSupportInjectionModule.class </code> is added because we used support Fragment. <code> AndroidInjectionModule </code> binds your <code> app.Fragment </code> to dagger. But If you want to use injection in <code> v4.fragment </code> then you should add <code> AndroidSupportInjectionModule.class </code> to your <code> AppComponent </code> modules.

Following is the boilerplate that gets generated for you when you use <code> @ContributesAndroidInjector </code>:

```java
@Module(subcomponents = ActivityBindingModule_DetailsActivity.DetailsActivitySubcomponent.class)
public abstract class ActivityBindingModule_DetailsActivity {
  private ActivityBindingModule_DetailsActivity() {}
  
  @Binds
  @IntoMap
  @ActivityKey(DetailsActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindAndroidInjectorFactory(
      DetailsActivitySubcomponent.Builder builder);
  
  @Subcomponent(modules = MovieDetailsModule.class)
  @ActivityScope
  public interface DetailsActivitySubcomponent extends AndroidInjector<DetailsActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DetailsActivity> {}
  }
  
}
```

#### So, What <code> @ContributesAndroidInjector </code> do?
- It generates the <code> DetailsActivitySubcomponent </code>.
- It adds the necessary <code> @Subcomponent </code> annotation for us.
- It adds an entry of (<code> DetailsActivity.class </code>, <code> DetailsActivitySubcomponent.Builder </code>) to the Map of Injector Factories used by <code> DispatchingAndroidInjector </code>. <code> Dagger-Android </code> uses this entry to build our <code> DetailsActivitySubcomponent </code> and perform injections for <code> DetailsActivity </code>.
- Also, binds DetailsActivity to the object-graph.
## Screenshots
<img src="https://user-images.githubusercontent.com/16334887/34884731-51a45c7e-f7c6-11e7-9034-f867bc03bf30.png" width="250"/> <img src="https://user-images.githubusercontent.com/16334887/34884881-c8b36e36-f7c6-11e7-9045-e4a1a4a66c98.png" width="250"/> <img src="https://user-images.githubusercontent.com/16334887/34884920-edd43218-f7c6-11e7-9b2e-7c68566f74ee.png" width="250"/> <img src="https://user-images.githubusercontent.com/16334887/34884954-07e67bde-f7c7-11e7-9e92-8192407a93a8.png" width="250"/> <img src="https://user-images.githubusercontent.com/16334887/34884999-2febb6b2-f7c7-11e7-8949-7987b4181ce0.png" width="250"/> <img src="https://user-images.githubusercontent.com/16334887/34885031-480857c8-f7c7-11e7-85f2-1831977b8de5.png" width="250"/>

## Libraries
This version of the app uses some other libraries:
- Dagger 2: externalize the creation of dependencies from the classes that use them
- Picasso: used for loading, processing, caching and displaying remote and local images.
- ButterKnife: used for perform injection on objects, views and OnClickListeners.
- CardView: used for representing the information in a card manner with a drop shadow and corner radius which looks consistent across the platform.
- RecyclerView: The RecyclerView widget is a more advanced and flexible version of ListView.
- GSON: Gson is a Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object.
- Retrofit: This library used to send HTTP request to the server and retrieve response.
- ROOM Library: Room provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.
- BlurView Library: It blurs its underlying content and draws it as a background for its children.

# The Movie DB API Key is required.
In order for the movieapp-mvp-clean app to function properly as of January 26th, 2018 an API key for themoviedb.org must be included with the build.

Include the unique key for the build by adding the following line to util/Constants.java or find the TODO Line.

<code> API_KEY = ""; </code>
<br/>
<br/>

## License
```
Copyright (C) 2018 Shehab Salah Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
  
  http://www.apache.org/licenses/LICENSE-2.0
  
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
